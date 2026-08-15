package adastra.backend.repository;

import adastra.backend.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CitiesRepository extends JpaRepository<City, UUID> {
}
