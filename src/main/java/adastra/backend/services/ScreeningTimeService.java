package adastra.backend.services;

import adastra.backend.DTO.ScreeningTimeDTO;
import adastra.backend.DTO.ScreeningTimeMappedDTO;
import adastra.backend.DTO.ScreeningTimeResponseDTO;
import adastra.backend.entities.Movie;
import adastra.backend.entities.Screen;
import adastra.backend.entities.ScreeningTime;
import adastra.backend.exceptions.NotFoundException;
import adastra.backend.repository.ScreeningTimesRepository;
import adastra.backend.softDelete.SoftDeleteMethod;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ScreeningTimeService extends SoftDeleteMethod<ScreeningTime, UUID> {

    private ScreeningTimesRepository screeningTimesRepository;
    private MoviesService moviesService;
    private ScreensService screensService;


    public List<ScreeningTimeMappedDTO> findAll(UUID cinemaId) {

        List<ScreeningTime> listScreenTime = this.screeningTimesRepository.findTimeByCinema(cinemaId);
        return listScreenTime.stream()
                .sorted(Comparator.comparing(ScreeningTime::getDateTime))
                .collect(Collectors.groupingBy(
                        ScreeningTime::getMovieId,
                        Collectors.groupingBy(
                                screen -> screen.getDateTime().toLocalDate(),
                                TreeMap::new,
                                Collectors.mapping(
                                        screeningTime -> new ScreeningTimeResponseDTO(
                                                screeningTime.getDateTime(),
                                                screeningTime.getScreenId().getId(),
                                                screeningTime.getScreenId().getScreenNumber()
                                        ),
                                        Collectors.toList()
                                )
                        )
                ))
                .entrySet()
                .stream()
                .map(entry -> new ScreeningTimeMappedDTO(
                        entry.getKey(),
                        entry.getValue()
                ))
                .toList();
    }


    public ScreeningTime save(ScreeningTimeDTO body) {
        Movie movieFound = this.moviesService.findById(body.movieId());
        Screen screenFound = this.screensService.findById(body.screenId());

        return this.screeningTimesRepository.save(new ScreeningTime(LocalDateTime.parse(body.dateTime()), movieFound, screenFound));
    }

    public void updateScreening(ScreeningTimeDTO body, UUID screeningId) {
        ScreeningTime found = this.findById(screeningId);
        found.setDateTime(LocalDateTime.parse(body.dateTime()));
        Movie movieFound = this.moviesService.findById(body.movieId());
        Screen screenFound = this.screensService.findById(body.screenId());
        found.setMovieId(movieFound);
        found.setScreenId(screenFound);
        this.screeningTimesRepository.save(found);
    }

    public ScreeningTime findById(UUID screeningId) {
        return this.screeningTimesRepository.findById(screeningId).orElseThrow(() -> new NotFoundException("Nessun cinema trovato con questo id"));
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
