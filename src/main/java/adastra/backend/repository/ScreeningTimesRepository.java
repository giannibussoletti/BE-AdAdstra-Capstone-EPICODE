package adastra.backend.repository;

import adastra.backend.entities.Screen;
import adastra.backend.entities.ScreeningTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ScreeningTimesRepository extends JpaRepository<ScreeningTime, UUID>, JpaSpecificationExecutor<ScreeningTime> {

    List<ScreeningTime> findScreeningTimeByScreenId(Screen screen);

}
