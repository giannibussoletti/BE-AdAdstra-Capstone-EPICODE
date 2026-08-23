package adastra.backend.controllers;

import adastra.backend.DTO.FilterByCinemaDTO;
import adastra.backend.DTO.FilterSeatsDTO;
import adastra.backend.DTO.FindTicketByScreenTimeIdDTO;
import adastra.backend.DTO.ScreeningTimeMappedDTO;
import adastra.backend.entities.Cinema;
import adastra.backend.entities.Seat;
import adastra.backend.services.CinemasService;
import adastra.backend.services.ScreeningTimeService;
import adastra.backend.services.SeatsService;
import adastra.backend.services.TicketsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/public")
public class PublicAccessController {

    private CinemasService cinemasService;
    private ScreeningTimeService screeningTimeService;
    private SeatsService seatsService;
    private TicketsService ticketsService;

    @PostMapping("/tickets")
    @ResponseStatus(HttpStatus.OK)
    public List<Seat> getSeatsByScreeningTimeId(@RequestBody FindTicketByScreenTimeIdDTO body) {
        return this.ticketsService.findSeatByScreeningTime(body.screeningTimeId());
    }

    @GetMapping("/cinemas")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Cinema> getCinemas() {
        return this.cinemasService.findAll();
    }

    @PostMapping("/screening-times")
    @ResponseStatus(HttpStatus.OK)
    public List<ScreeningTimeMappedDTO> getScreeningTime(@RequestBody FilterByCinemaDTO body) {
        return this.screeningTimeService.findAll(body.cinemaId());
    }

    @PostMapping("/seats")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Seat> getSeats(@RequestBody FilterSeatsDTO body) {

        return this.seatsService.findAll(body.cinemaId(), body.screenId());
    }

}
