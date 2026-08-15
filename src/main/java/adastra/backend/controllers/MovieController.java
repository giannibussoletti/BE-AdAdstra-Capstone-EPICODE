package adastra.backend.controllers;

import adastra.backend.DTO.DeleteMovieDTO;
import adastra.backend.DTO.MovieDTO;
import adastra.backend.DTO.ResponseDTO;
import adastra.backend.DTO.ResponseDeleteMovieDTO;
import adastra.backend.entities.Movie;
import adastra.backend.services.MoviesService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/movies")
@AllArgsConstructor
public class MovieController {

    private MoviesService moviesService;


    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Page<Movie> getMovies(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "title") String searchBy) {

        return this.moviesService.findAll(page, size, searchBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO save(@RequestBody MovieDTO body) {
        Movie saved = this.moviesService.save(body);
        return new ResponseDTO("Film salvato correttamente", saved.getId(), LocalDateTime.now());
    }

    @PutMapping("/{movieId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO findMovieAndUpdate(@PathVariable UUID movieId, @RequestBody MovieDTO body) {
        Movie updated = this.moviesService.findMovieAndUpdate(movieId, body);
        return new ResponseDTO("Film aggiornato con successo", updated.getId(), LocalDateTime.now());
    }

    @PatchMapping("/{movieId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDeleteMovieDTO softDeleteMovie(@PathVariable UUID movieId, @RequestBody DeleteMovieDTO body) {
        this.moviesService.softDeleteMovie(movieId, body);
        return new ResponseDeleteMovieDTO("visibilità del film aggiornata", LocalDateTime.now());
    }

}
