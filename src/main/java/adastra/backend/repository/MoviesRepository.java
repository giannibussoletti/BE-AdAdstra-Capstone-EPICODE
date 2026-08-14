package adastra.backend.repository;

import adastra.backend.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MoviesRepository extends JpaRepository<Movie, UUID> {
}
