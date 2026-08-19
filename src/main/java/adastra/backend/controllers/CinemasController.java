package adastra.backend.controllers;

import adastra.backend.DTO.*;
import adastra.backend.entities.Cinema;
import adastra.backend.services.CinemasService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/cinemas")
public class CinemasController {

    private CinemasService cinemasService;


    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Cinema> getCinemas() {
        return this.cinemasService.findAll();
    }

    @PostMapping("/{city}")
    @ResponseStatus(HttpStatus.OK)
    public Cinema findCinemaByCity(@RequestBody FindCinemaByCityDTO body, @PathVariable String city) {
        return this.cinemasService.findByCityID(body.cityId()).getFirst();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO save(@Valid @RequestBody CinemaDTO body) {
        Cinema saved = this.cinemasService.save(body);
        return new ResponseDTO("Cinema salvato correttamente", saved.getId(), LocalDateTime.now());
    }

    @PutMapping("/{cinemaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCinema(@Valid @RequestBody CinemaDTO body, @PathVariable UUID cinemaId) {
        this.cinemasService.cinemaUpdate(body, cinemaId);
    }

    @PatchMapping("/delete/{cinemaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseNoIdDTO softDelete(@RequestBody DeleteDTO body, @PathVariable UUID cinemaId) {
        this.cinemasService.softDeleteGeneric(cinemaId, body.deletion());
        return new ResponseNoIdDTO("Stato del cinema aggiornato correttamente", LocalDateTime.now());
    }

}
