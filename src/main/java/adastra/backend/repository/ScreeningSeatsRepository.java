package adastra.backend.repository;

import adastra.backend.entities.ScreeningSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ScreeningSeatsRepository extends JpaRepository<ScreeningSeat, UUID> {
}
