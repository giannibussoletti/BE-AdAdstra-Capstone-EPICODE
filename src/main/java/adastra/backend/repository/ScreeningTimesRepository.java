package adastra.backend.repository;

import adastra.backend.entities.ScreeningTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ScreeningTimesRepository extends JpaRepository<ScreeningTime, UUID>, JpaSpecificationExecutor<ScreeningTime> {

    @Query("SELECT s FROM ScreeningTime s JOIN s.screenId sc JOIN sc.cinemaId c WHERE c.id = :cinemaId")
    List<ScreeningTime> findTimeByCinema(@Param("cinemaId") UUID cinemaId);
}
