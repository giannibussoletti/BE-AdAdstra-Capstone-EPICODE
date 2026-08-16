package adastra.backend.services;

import adastra.backend.DTO.ScreeningTimeDTO;
import adastra.backend.entities.Movie;
import adastra.backend.entities.Screen;
import adastra.backend.entities.ScreeningTime;
import adastra.backend.repository.ScreeningTimesRepository;
import adastra.backend.softDeletion.SoftDeleteMethod;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ScreeningTimeService extends SoftDeleteMethod<ScreeningTime, UUID> {

    private ScreeningTimesRepository screeningTimesRepository;
    private MoviesService moviesService;
    private ScreensService screensService;

    public List<ScreeningTime> findAll() {
        return this.screeningTimesRepository.findAll();
    }

    public ScreeningTime save(ScreeningTimeDTO body) {
        Movie movieFound = this.moviesService.findById(body.movieId());
        Screen screenFound = this.screensService.findById(body.screenId());

        return this.screeningTimesRepository.save(new ScreeningTime(body.dateTime(), movieFound, screenFound));
    }

    @Override
    protected JpaRepository<ScreeningTime, UUID> getRepository() {
        return this.screeningTimesRepository;
    }

    @Override
    protected String getEntityName() {
        return "orario di proiezione";
    }

    @Override
    public void softDeleteGeneric(UUID entityId, String body) {
        super.softDeleteGeneric(entityId, body);
    }
}
