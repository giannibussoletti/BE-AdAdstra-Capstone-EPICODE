package adastra.backend.repository;

import adastra.backend.entities.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SeatsRepository extends JpaRepository<Seat, UUID>, JpaSpecificationExecutor<Seat> {

}
