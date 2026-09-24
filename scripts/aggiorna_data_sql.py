#!/usr/bin/env python3
"""
aggiorna_data_sql.py - rigenera le date di AdAstra in data.sql

Cosa fa
  * aggiorna la release_date dei film (rispetto al primo giorno di programmazione)
  * riscrive TUTTI i blocchi INSERT INTO screening_time, a partire da oggi
    (o da --data-inizio) per --giorni giorni
  * lascia intatto tutto il resto (città, cinema, sale, trama, poster, posti...)

Regole di programmazione (le stesse del file originale)
  * un film viene proiettato solo se release_date <= giorno
  * ogni sala proietta un solo film al giorno
  * spettacoli consecutivi: durata del film + 20 minuti
  * feriali: primo spettacolo tra le 15:00 e le 15:30
    sabato/domenica: primo spettacolo tra le 11:30 e le 12:00
  * ultimo spettacolo che inizia entro le 23:45

Uso
  python aggiorna_data_sql.py                       # da oggi, 7 giorni, sovrascrive data.sql (crea data.sql.bak)
  python aggiorna_data_sql.py -i src/main/resources/data.sql
  python aggiorna_data_sql.py --giorni 14
  python aggiorna_data_sql.py --data-inizio 2026-09-24
  python aggiorna_data_sql.py --date-reali          # usa le date di uscita reali, non quelle "rolling"
  python aggiorna_data_sql.py -o nuovo_data.sql     # non sovrascrive l'originale

Non servono librerie esterne (solo Python 3.8+).
"""
import argparse
import random
import re
import shutil
import sys
import uuid
from datetime import date, datetime, timedelta
from pathlib import Path

NL = "\r\n"  # il file originale usa CRLF
NAMESPACE = uuid.UUID("6f1c2b7e-0c53-4b1e-9d0e-5a7a1d3c9e11")  # ID delle proiezioni stabili tra un run e l'altro

PAUSA_MIN = 20          # minuti tra la fine di un film e l'inizio del successivo
ULTIMO_INIZIO = 23 * 60 + 45
MAX_SPETTACOLI = 5
GIORNI_IT = ["Lunedi", "Martedi", "Mercoledi", "Giovedi", "Venerdi", "Sabato", "Domenica"]

# id film: (titolo, data di uscita reale, offset in giorni rispetto al primo giorno di programmazione, popolarità)
# - l'offset serve per la modalità "rolling": il film esce sempre N giorni dopo (o prima) dell'inizio
#   della programmazione, così il file ha sempre film già usciti e film in arrivo.
# - la popolarità decide quante sale/spettacoli ottiene il film.
# Un film nel file ma NON in questa tabella mantiene la sua release_date e ha popolarità 2.
FILM = {
    "ac377062-0940-42d8-b3ab-4edb050ed8ba": ("Spider-Man: Brand New Day", "2026-07-31", -43, 5.0),
    "5907c7d0-bd05-4ee1-9c33-0bd5a2426dbe": ("Camp Miasma", "2026-08-07", -36, 1.0),
    "edcde877-a8eb-4517-a144-dfd3f88538eb": ("One Night Only", "2026-08-07", -36, 2.5),
    "1ec659d1-9219-4cbd-91d7-43b17ab3b7b9": ("Insidious - Fuori dall'Altrove", "2026-08-19", -24, 3.5),
    "7fd65d01-22aa-4e5b-9f98-4c5dc339ed49": ("The Dog Stars", "2026-08-28", -15, 1.5),
    "cad99018-ea36-44da-a09d-12119d5c7edb": ("Coyote vs. Acme", "2026-09-02", -10, 2.0),
    "5b4690f9-854f-4b27-bcba-4932eb1b1cd8": ("Onslaught", "2026-09-04", -8, 3.0),
    "1765660e-7dbc-4928-a7f6-db2635d2a622": ("Resident Evil", "2026-09-18", 6, 3.5),
    "a6210149-058b-4021-b5da-d281e202cc61": ("Shaun, vita da pecora", "2026-09-18", 6, 3.0),
    "0c7490f0-d90e-4d30-abc2-edce7bf73241": ("L'isola dei ricordi", "2026-09-24", 12, 3.5),
    "b4bf5995-149e-486f-9ee5-d756e7a3c040": ("Dune - Parte tre", "2026-12-15", 94, 4.0),
    "d42bcd37-892e-454d-a46e-b0f757320633": ("Avengers: Doomsday", "2026-12-18", 97, 5.0),
}
POP_DEFAULT = 2.0


# --------------------------------------------------------------------------- parsing
def leggi_struttura(head):
    """Estrae città, cinema, sale e film dalla parte iniziale del file."""
    citta = {i: n for i, n in re.findall(r"\('([0-9a-f-]{36})', '([^']*)', 'FALSE'\)", head)}

    cinema = re.findall(
        r"\('([0-9a-f-]{36})', '[^']*', '[^']*', 'FALSE',\s*'([0-9a-f-]{36})'\)", head)
    cinema = [(cid, citta.get(city_id, "?")) for cid, city_id in cinema]

    sale = re.findall(
        r"\('([0-9a-f-]{36})', '(?:LEFT|RIGHT)', 'FALSE', (\d+), '([0-9a-f-]{36})'\)", head)
    sale_per_cinema = {cid: [] for cid, _ in cinema}
    for sid, numero, cid in sale:
        sale_per_cinema[cid].append((int(numero), sid))
    for v in sale_per_cinema.values():
        v.sort()

    film = {}
    i0 = head.index("INSERT INTO movies")
    for m in re.finditer(r"\('([0-9a-f-]{36})', (\d+), 'FALSE',", head[i0:]):
        mid, durata = m.group(1), int(m.group(2))
        resto = head[i0 + m.end():]
        t = re.search(r"'(\d{4}-\d\d-\d\d)',\s*'(?:[^']|'')*',\s*'((?:[^']|'')*)'", resto)
        film[mid] = {
            "durata": durata,
            "uscita": date.fromisoformat(t.group(1)),
            "titolo": t.group(2).replace("''", "'").strip(),
        }
    return cinema, sale_per_cinema, film


def aggiorna_uscite(head, inizio, date_reali):
    """Riscrive la release_date dei film noti."""
    i0 = head.index("INSERT INTO movies")
    for mid, (_, reale, offset, _) in FILM.items():
        pos = head.find(f"'{mid}'", i0)
        if pos < 0:
            continue
        nuova = reale if date_reali else (inizio + timedelta(days=offset)).isoformat()
        m = re.compile(r"'\d{4}-\d\d-\d\d'").search(head, pos)
        head = head[:m.start()] + f"'{nuova}'" + head[m.end():]
    return head


# --------------------------------------------------------------------------- generazione
def scegli_film_del_giorno(rnd, attivi, n_sale):
    """Ogni film attivo compare almeno una volta (se le sale bastano); le sale in più vanno ai film più popolari."""
    def peso(mid):
        return FILM.get(mid, (None, None, None, POP_DEFAULT))[3]

    ids = sorted(attivi)
    if len(ids) >= n_sale:
        scelti = []
        pool = ids[:]
        while len(scelti) < n_sale:
            f = rnd.choices(pool, weights=[peso(x) for x in pool])[0]
            pool.remove(f)
            scelti.append(f)
        return scelti
    scelti = ids[:]
    while len(scelti) < n_sale:
        pool = [x for x in ids if scelti.count(x) < 3]
        scelti.append(rnd.choices(pool, weights=[peso(x) for x in pool])[0])
    return scelti


def assegna_alle_sale(rnd, scelti, sale_per_cinema):
    """Distribuisce i film sulle sale evitando più di 2 sale con lo stesso film nello stesso cinema."""
    slot = [(cid, num, sid) for cid, sale in sale_per_cinema.items() for num, sid in sale]
    for _ in range(500):
        rnd.shuffle(scelti)
        per_cinema = {}
        ok = True
        for (cid, _, _), mid in zip(slot, scelti):
            per_cinema.setdefault(cid, []).append(mid)
        for lista in per_cinema.values():
            if any(lista.count(x) > 2 for x in lista):
                ok = False
                break
        if ok:
            break
    return list(zip(slot, scelti))


def orari_sala(rnd, giorno, durata, popolarita):
    inizi = ([11 * 60 + 30, 11 * 60 + 40, 11 * 60 + 50, 12 * 60] if giorno.weekday() >= 5
             else [15 * 60, 15 * 60 + 10, 15 * 60 + 20, 15 * 60 + 30])
    start = rnd.choice(inizi)
    step = durata + PAUSA_MIN
    n_max = min(MAX_SPETTACOLI, (ULTIMO_INIZIO - start) // step + 1)
    if popolarita >= 2:
        n = rnd.randint(2, max(2, n_max))
    else:
        n = rnd.randint(1, min(3, n_max))
    return [start + i * step for i in range(min(n, n_max))]


def genera_blocchi(inizio, giorni, cinema, sale_per_cinema, film, seed):
    blocchi = []
    for d in range(giorni):
        giorno = inizio + timedelta(days=d)
        rnd = random.Random(f"{seed}|{giorno.isoformat()}")
        attivi = [m for m, f in film.items() if f["uscita"] <= giorno]
        n_sale = sum(len(v) for v in sale_per_cinema.values())
        if not attivi:
            continue

        scelti = scegli_film_del_giorno(rnd, attivi, n_sale)
        assegnazioni = assegna_alle_sale(rnd, scelti, sale_per_cinema)

        righe = []
        for cid, nome_citta in cinema:
            righe.append(f"    -- Adastra {nome_citta.capitalize()}")
            for (c, numero, sid), mid in assegnazioni:
                if c != cid:
                    continue
                f = film[mid]
                pop = FILM.get(mid, (None, None, None, POP_DEFAULT))[3]
                for minuti in orari_sala(rnd, giorno, f["durata"], pop):
                    dt = datetime.combine(giorno, datetime.min.time()) + timedelta(minutes=minuti)
                    quando = dt.strftime("%Y-%m-%d %H:%M:%S")
                    pid = uuid.uuid5(NAMESPACE, f"{sid}|{quando}")
                    righe.append((f"    ('{pid}', '{quando}', 'FALSE', '{mid}',", f"     '{sid}')",
                                  f"-- {f['titolo']} (sala {numero})"))

        # ultima riga dati -> ';' invece di ','
        out = [f"-- ---------- {GIORNI_IT[giorno.weekday()]} {giorno.strftime('%d/%m/%Y')} ----------",
               "INSERT INTO screening_time (id, date_time, screening_time_is_deleted, movie_id, screen_id)",
               "VALUES"]
        idx_ultima = max(i for i, r in enumerate(righe) if isinstance(r, tuple))
        for i, r in enumerate(righe):
            if isinstance(r, str):
                out.append(r)
            else:
                chiusura = ";" if i == idx_ultima else ","
                out.append(r[0])
                out.append(f"{r[1]}{chiusura} {r[2]}")
        blocchi.append(NL.join(out) + NL)
    return blocchi


# --------------------------------------------------------------------------- main
def main():
    ap = argparse.ArgumentParser(description="Aggiorna date e orari di AdAstra in data.sql")
    ap.add_argument("-i", "--input", default="data.sql", help="file da leggere (default: data.sql)")
    ap.add_argument("-o", "--output", help="file da scrivere (default: sovrascrive l'input, con backup .bak)")
    ap.add_argument("--data-inizio", help="primo giorno di programmazione, YYYY-MM-DD (default: oggi)")
    ap.add_argument("--giorni", type=int, default=7, help="quanti giorni di programmazione (default: 7)")
    ap.add_argument("--date-reali", action="store_true",
                    help="usa le date di uscita reali invece di quelle relative a oggi")
    ap.add_argument("--seed", default="adastra", help="cambia il seed per ottenere una programmazione diversa")
    a = ap.parse_args()

    src = Path(a.input)
    with open(src, encoding="utf-8", newline="") as fh:
        testo = fh.read()

    try:
        i_giorni = testo.index("-- ----------", testo.index("INSERT INTO movies"))
        i_fine = testo.index("-- NON TOCCARE VANNO BENE")
    except ValueError:
        sys.exit("Non trovo i punti di riferimento nel file (blocco movies / commento '-- NON TOCCARE VANNO BENE').")

    inizio = date.fromisoformat(a.data_inizio) if a.data_inizio else date.today()
    head = aggiorna_uscite(testo[:i_giorni], inizio, a.date_reali)
    coda = testo[i_fine:]

    cinema, sale_per_cinema, film = leggi_struttura(head)
    blocchi = genera_blocchi(inizio, a.giorni, cinema, sale_per_cinema, film, a.seed)

    nuovo = head + (NL).join(blocchi) + NL + NL + coda

    dst = Path(a.output) if a.output else src
    if dst == src:
        shutil.copyfile(src, str(src) + ".bak")
    with open(dst, "w", encoding="utf-8", newline="") as fh:
        fh.write(nuovo)

    print(f"OK: {dst}  |  programmazione {inizio:%d/%m/%Y} -> {inizio + timedelta(days=a.giorni - 1):%d/%m/%Y}")
    for mid, f in sorted(film.items(), key=lambda x: x[1]["uscita"]):
        stato = "in sala" if f["uscita"] <= inizio else "in arrivo"
        print(f"  {f['uscita']:%d/%m/%Y}  {stato:9}  {f['titolo']}")


if __name__ == "__main__":
    main()
