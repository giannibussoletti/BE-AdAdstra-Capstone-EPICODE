package adastra.backend.controllers;

import adastra.backend.DTO.DeleteDTO;
import adastra.backend.DTO.MovieDTO;
import adastra.backend.DTO.ResponseDTO;
import adastra.backend.DTO.ResponseNoIdDTO;
import adastra.backend.entities.Movie;
import adastra.backend.services.MoviesService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/movies")
@AllArgsConstructor
public class MovieController {

    private MoviesService moviesService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO save(@Valid @RequestBody MovieDTO body) {
        Movie saved = this.moviesService.save(body);
        return new ResponseDTO("Film salvato correttamente", saved.getId(), LocalDateTime.now());
    }

    @PutMapping("/{movieId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO findMovieAndUpdate(@PathVariable UUID movieId, @Valid @RequestBody MovieDTO body) {
        Movie updated = this.moviesService.findMovieAndUpdate(movieId, body);
        return new ResponseDTO("Film aggiornato con successo", updated.getId(), LocalDateTime.now());
    }

    @PatchMapping("/{movieId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseNoIdDTO softDeleteMovie(@PathVariable UUID movieId, @RequestBody DeleteDTO body) {
        this.moviesService.softDeleteGeneric(movieId, body.deletion());
        return new ResponseNoIdDTO("visibilità del film aggiornata", LocalDateTime.now());
    }

}
