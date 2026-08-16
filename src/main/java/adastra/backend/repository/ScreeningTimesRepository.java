package adastra.backend.repository;

import adastra.backend.entities.Cinema;
import adastra.backend.entities.Screen;
import adastra.backend.entities.ScreeningTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ScreeningTimesRepository extends JpaRepository<ScreeningTime, UUID> {

    List<ScreeningTime> findScreeningTimeByScreenId(Screen screen);

    @Query("SELECT st FROM ScreeningTime st WHERE st.screenId.cinemaId = :cinemaId")
    List<ScreeningTime> filterTimesFromCinema(@Param("cinemaId") Cinema cinemaId);

}
