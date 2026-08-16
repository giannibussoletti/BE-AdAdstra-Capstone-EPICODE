package adastra.backend.controllers;

import adastra.backend.DTO.DeleteDTO;
import adastra.backend.DTO.FilterMoviesDTO;
import adastra.backend.DTO.ResponseDTO;
import adastra.backend.DTO.ScreeningTimeDTO;
import adastra.backend.entities.ScreeningTime;
import adastra.backend.services.ScreeningTimeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/screening-times")
@AllArgsConstructor
public class ScreeningTimesController {

    private ScreeningTimeService screeningTimeService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ScreeningTime> getScreeningTime(@RequestBody FilterMoviesDTO body) {
        return this.screeningTimeService.findAll(body.cinemaId(), body.movieId());
    }

    /*public  List<ScreeningTime> findAll() {
        return this.screeningTimeService.findAll();
    }*/
   /* @GetMapping("/{cinemaId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ScreeningTime> findByCinemaId(@PathVariable UUID cinemaId) {
        return this.screeningTimeService.findByCinemaId(cinemaId);
    }

    @GetMapping("/{movieId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ScreeningTime> findByMovieId(@PathVariable UUID movieId) {
        return this.screeningTimeService.findByCinemaId(movieId);
    }*/

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO save(@Valid @RequestBody ScreeningTimeDTO body) {
        ScreeningTime saved = this.screeningTimeService.save(body);
        return new ResponseDTO("Orario salvato correttamente", saved.getId(), LocalDateTime.now());
    }

    @PutMapping("/{screeningId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateScreening(@Valid @RequestBody ScreeningTimeDTO body, @PathVariable UUID screeningId) {
        this.screeningTimeService.updateScreening(body, screeningId);
    }

    @PatchMapping("/delete/{screeningId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteScreening(@RequestBody DeleteDTO body, @PathVariable UUID screeningId) {
        this.screeningTimeService.softDeleteGeneric(screeningId, body.deletion());
    }
}
