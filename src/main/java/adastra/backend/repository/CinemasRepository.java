package adastra.backend.repository;

import adastra.backend.entities.Cinema;
import adastra.backend.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CinemasRepository extends JpaRepository<Cinema, UUID> {

    Optional<List<Cinema>> findCinemaByCityId(City city);
}
