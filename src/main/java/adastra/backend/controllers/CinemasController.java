package adastra.backend.controllers;

import adastra.backend.DTO.CinemaDTO;
import adastra.backend.DTO.DeleteDTO;
import adastra.backend.DTO.ResponseDTO;
import adastra.backend.DTO.ResponseDeleteDTO;
import adastra.backend.entities.Cinema;
import adastra.backend.services.CinemasService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("cinemas")
public class CinemasController {

    private CinemasService cinemasService;


    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Page<Cinema> getCinemas(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "cinemaName") String searchBy) {
        return this.cinemasService.findAll(page, size, searchBy);
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
    public ResponseDeleteDTO softDelete(@RequestBody DeleteDTO body, @PathVariable UUID cinemaId) {
        this.cinemasService.softDeleteGeneric(cinemaId, body.deletion());
        return new ResponseDeleteDTO("Stato del cinema aggiornato correttamente", LocalDateTime.now());
    }

}
