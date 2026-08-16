package adastra.backend.services;

import adastra.backend.DTO.MovieDTO;
import adastra.backend.entities.Movie;
import adastra.backend.exceptions.NotFoundException;
import adastra.backend.repository.MoviesRepository;
import adastra.backend.softDeletion.SoftDeleteMethod;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class MoviesService extends SoftDeleteMethod<Movie, UUID> {

    private MoviesRepository moviesRepository;

    public Page<Movie> findAll(int page, int size, String searchBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(searchBy));
        return this.moviesRepository.findAll(pageable);
    }

    public Movie findById(UUID movieId) {
        return this.moviesRepository.findById(movieId).orElseThrow(() -> new NotFoundException("nessun film con questo id trovato"));
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
        Movie found = this.findById(movieId);
        found.setDuration(body.duration());
        found.setPlot(body.plot());
        found.setTitle(body.title());
        found.setPosterLink(body.posterLink());
        found.setStarring(body.starring());
        return this.moviesRepository.save(found);
    }


    @Override
    protected JpaRepository<Movie, UUID> getRepository() {
        return this.moviesRepository;
    }

    @Override
    protected String getEntityName() {
        return "film";
    }

    @Override
    public void softDeleteGeneric(UUID entityId, String body) {
        super.softDeleteGeneric(entityId, body);
    }
}
