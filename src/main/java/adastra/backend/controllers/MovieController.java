package adastra.backend.controllers;

import adastra.backend.entities.Movie;
import adastra.backend.services.MoviesService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
}
