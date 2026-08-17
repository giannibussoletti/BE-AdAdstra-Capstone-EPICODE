package adastra.backend.controllers;

import adastra.backend.DTO.DeleteDTO;
import adastra.backend.DTO.FilterSeatsDTO;
import adastra.backend.DTO.ResponseSeatsDTO;
import adastra.backend.DTO.SeatDTO;
import adastra.backend.entities.Seat;
import adastra.backend.services.SeatsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/seats")
@AllArgsConstructor
public class SeatsController {

    private SeatsService seatsService;

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Seat> getSeats(@RequestBody FilterSeatsDTO body,
                               @RequestParam(required = false) Integer number,
                               @RequestParam(required = false) Character row) {

        return this.seatsService.findAll(body.cinemaId(), row, number, body.screenId());
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseSeatsDTO save(@Valid @RequestBody SeatDTO body) {
        ArrayList<UUID> saved = this.seatsService.save(body);
        return new ResponseSeatsDTO("posto salvato correttamente", saved, LocalDateTime.now());
    }

    @PatchMapping("/{seatId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDelete(@RequestBody DeleteDTO body, UUID seatId) {
        this.seatsService.softDeleteGeneric(seatId, body.deletion());
    }
}
