package adastra.backend.repository;

import adastra.backend.entities.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ScreensRepository extends JpaRepository<Screen, UUID> {
}
