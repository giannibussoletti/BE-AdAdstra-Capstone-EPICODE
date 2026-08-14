package adastra.backend.services;

import adastra.backend.DTO.MovieDTO;
import adastra.backend.entities.Movie;
import adastra.backend.repository.MoviesRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MoviesService {

    private MoviesRepository moviesRepository;

    public Page<Movie> findAll(int page, int size, String searchBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(searchBy));
        return this.moviesRepository.findAll(pageable);
    }

    public Movie save(MovieDTO payload) {
        return this.moviesRepository.save(new Movie(
                payload.starring(),
                payload.duration(),
                payload.title(),
                payload.posterLink(),
                payload.plot()));
    }

}
