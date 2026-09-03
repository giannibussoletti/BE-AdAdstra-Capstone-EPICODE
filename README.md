# 🎬 Adastra Cinema — Back-End

Back-end della web application **Adastra Cinema**, un sistema di gestione per una catena di cinema: espone le API utilizzate dal front-end per la consultazione di cinema, film, orari, la prenotazione dei posti, l'acquisto dei biglietti e la gestione degli utenti.

> 🔗 Repository Front-End: [FE-AdAdstra-Capstone-EPICODE](https://github.com/giannibussoletti/FE-AdAdstra-Capstone-EPICODE)

---

## 🛠️ Tech Stack

- **Java**
- **Spring (Spring Boot)**
- **PostgreSQL** (database)
- **Maven** (gestione delle dipendenze, integrato in IntelliJ)
- **IntelliJ IDEA** (IDE consigliato per la simulazione del server)
- **JWT** per l'autenticazione
- **Cloudinary** per lo storage delle immagini

---

## 📋 Prerequisiti

| Strumento          | Note                                                    | Download                                                       |
| ------------------ | ------------------------------------------------------- | -------------------------------------------------------------- |
| JDK                | versione 17 o superiore (compatibile con Spring Boot 3) | [Eclipse Temurin](https://adoptium.net/)                       |
| IntelliJ IDEA      | Community Edition è sufficiente                         | [jetbrains.com/idea](https://www.jetbrains.com/idea/download/) |
| PostgreSQL         | per la creazione del database                           | [postgresql.org](https://www.postgresql.org/download/)         |
| Account Cloudinary | per la gestione delle immagini (es. avatar utente)      | [cloudinary.com](https://cloudinary.com/)                      |

> ℹ️ **Cos'è Cloudinary?** È un servizio cloud che si occupa di storage, ottimizzazione e distribuzione di immagini e altri file multimediali. Adastra Cinema lo usa per non dover salvare le immagini direttamente sul server, ma su uno storage esterno dedicato.

> Maven non richiede un'installazione separata: IntelliJ IDEA lo gestisce automaticamente e scarica i pacchetti necessari all'apertura del progetto.

---

## ⚙️ Variabili d'ambiente

Prima di avviare il progetto è necessario configurare manualmente le seguenti variabili (ad esempio nel file `application.properties`/`application.yml` oppure nelle variabili d'ambiente della run configuration di IntelliJ):

```
PORT=5555
DB_URL=
DB_USERNAME=
DB_PASSWORD=
JWT_SECRET=

CLOUDINARY_API_KEY=
CLOUDINARY_SECRET=
CLOUDINARY_NAME=
```

I valori di `CLOUDINARY_*` si ottengono creando un account gratuito su [Cloudinary](https://cloudinary.com/) e recuperandoli dalla dashboard.

---

## 🚀 Installazione e avvio

1. **Clona il repository**

   ```bash
   git clone https://github.com/giannibussoletti/BE-AdAdstra-Capstone-EPICODE.git
   ```

2. **Crea il database** su PostgreSQL che verrà referenziato in `DB_URL`.

3. **Apri il progetto in IntelliJ IDEA**: Maven scaricherà automaticamente le dipendenze necessarie.

4. **Configura le variabili d'ambiente** elencate sopra (tramite un file env.properties salvato nella root).

5. **Avvia l'applicazione** eseguendo la classe main del progetto Spring Boot.

6. Il server sarà disponibile all'indirizzo `http://localhost:PORT` (di default `5555`, in base al valore impostato).

> 🔗 Per usare l'applicazione completa, avvia anche il [front-end](https://github.com/giannibussoletti/FE-AdAdstra-Capstone-EPICODE).

---

## 🔐 Autenticazione e ruoli

L'autenticazione è gestita tramite **JWT**, passato nelle richieste come header `Authorization: Bearer <token>`.

Sono previsti tre ruoli utente:

| Ruolo          | Descrizione                                                                    |
| -------------- | ------------------------------------------------------------------------------ |
| **USER**       | Cliente normale del sito                                                       |
| **ADMIN**      | Gestore del sito, si occupa dell'inserimento degli orari                       |
| **SUPERADMIN** | Gestione generale dei dati: creazione di città, cinema e informazioni sui film |

---

## 📡 Punti di accesso (API Endpoints)

### Pubblici (nessuna autenticazione richiesta)

| Metodo | Endpoint                  | Descrizione                                                                               |
| ------ | ------------------------- | ----------------------------------------------------------------------------------------- |
| GET    | `/public/cinemas`         | Lista dei cinema                                                                          |
| GET    | `/public/screening-times` | Lista degli orari di proiezione                                                           |
| GET    | `/public/seats`           | Lista dei posti per la costruzione della mappa del cinema                                 |
| GET    | `/public/tickets`         | Lista dei biglietti già prenotati (in base all'id dell'orario), senza attributi sensibili |
| POST   | `/public/bookings`        | Salvataggio di una prenotazione senza account                                             |

### Gestione utente e autenticazione

| Metodo | Endpoint                  | Descrizione                                            |
| ------ | ------------------------- | ------------------------------------------------------ |
| POST   | `/auth/registration`      | Registrazione utente                                   |
| POST   | `/auth/login`             | Login utente                                           |
| GET    | `/auth/token`             | Verifica del token a una nuova connessione dell'utente |
| POST   | `/bookings`               | Registrazione delle prenotazioni con account loggato   |
| GET    | `/tickets/user-movies`    | Lista dei film visti dall'utente, mostrata sul profilo |
| PATCH  | `/user/profile/password`  | Aggiornamento password                                 |
| PATCH  | `/user/profile/new-email` | Aggiornamento email                                    |
| PATCH  | `/user/profile/avatar`    | Aggiornamento avatar                                   |

---

## 🗺️ Roadmap del progetto

- Gestione prevendita per i film di prossima uscita
- Gestione della mappa dei posti riservati alle persone diversamente abili
- Check di validazione del biglietto, per contrassegnarlo come usato e impedirne il riutilizzo
- Creazione e invio dei biglietti in formato PDF via email
- Creazione e gestioni di end-point amministrativi

---

## 🔗 Link utili

- [Repository Front-End](https://github.com/giannibussoletti/FE-AdAdstra-Capstone-EPICODE)
- [Download JDK (Eclipse Temurin)](https://adoptium.net/)
- [Download IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
- [Download PostgreSQL](https://www.postgresql.org/download/)
- [Cloudinary](https://cloudinary.com/)
