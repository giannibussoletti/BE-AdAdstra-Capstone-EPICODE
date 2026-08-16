package adastra.backend.repository;

import adastra.backend.entities.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CinemasRepository extends JpaRepository<Cinema, UUID> {
}
