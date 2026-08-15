package adastra.backend.services;

import adastra.backend.DTO.DeleteMovieDTO;
import adastra.backend.DTO.MovieDTO;
import adastra.backend.entities.Movie;
import adastra.backend.enums.MovieIsDeleted;
import adastra.backend.exceptions.NotFoundException;
import adastra.backend.exceptions.WrongBodyDeletionException;
import adastra.backend.repository.MoviesRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

    public Movie findMovieAndUpdate(UUID movieId, MovieDTO body) {
        Movie found = this.moviesRepository.findById(movieId).orElseThrow(() -> new NotFoundException("nessun film con questo id trovato"));
        found.setDuration(body.duration());
        found.setPlot(body.plot());
        found.setTitle(body.title());
        found.setPosterLink(body.posterLink());
        found.setStarring(body.starring());
        return this.moviesRepository.save(found);
    }

    public void softDeleteMovie(UUID movieId, DeleteMovieDTO body) {
        Movie found = this.moviesRepository.findById(movieId).orElseThrow(() -> new NotFoundException("nessun film con questo id trovato"));

        boolean bodyTrue = body.deletion().equalsIgnoreCase("true");
        boolean foundTrue = found.getMovieIsDeleted() == MovieIsDeleted.TRUE;


        if (bodyTrue && foundTrue) {
            throw new WrongBodyDeletionException("Il film è già disponibile");
        } else if (!bodyTrue && !foundTrue) {
            throw new WrongBodyDeletionException("Il film risulta già cancellato");
        }
        switch (body.deletion().toLowerCase()) {
            case "true" -> found.setMovieIsDeleted(MovieIsDeleted.TRUE);
            case "false" -> found.setMovieIsDeleted(MovieIsDeleted.FALSE);
            default -> throw new WrongBodyDeletionException("il body può contenere solo 'true' o 'false'");
        }
        this.moviesRepository.save(found);
    }

}
