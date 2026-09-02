package adastra.backend.services;

import adastra.backend.DTO.MovieDTO;
import adastra.backend.entities.Movie;
import adastra.backend.exceptions.NotFoundException;
import adastra.backend.repository.MoviesRepository;
import adastra.backend.softDelete.SoftDeleteMethod;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MoviesService extends SoftDeleteMethod<Movie, UUID> {

    private MoviesRepository moviesRepository;

    public List<Movie> findAll() {
        ;
        return this.moviesRepository.findAll();
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
                payload.plot(),
                payload.releaseDate(),
                payload.director(),
                payload.bannerLink(),
                payload.trailer(),
                payload.tagline()
        ));
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
