package adastra.backend.controllers;

import adastra.backend.DTO.*;
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

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Seat> getSeats(@RequestBody FilterSeatsDTO body,
                               @RequestParam(required = false) Integer number,
                               @RequestParam(required = false) Character row) {

        return this.seatsService.findAll(body.cinemaId(), row, number, body.screenId());
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseSeatsDTO save(@Valid @RequestBody SeatDTO body) {
        ArrayList<UUID> saved = this.seatsService.save(body);
        return new ResponseSeatsDTO("posto salvato correttamente", saved, LocalDateTime.now());
    }

    @PatchMapping("/{seatId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDeleteSeat(@RequestBody DeleteDTO body, @PathVariable UUID seatId) {
        this.seatsService.softDeleteGeneric(seatId, body.deletion());
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDeleteRow(@RequestBody DeleteRowDTO body) {
        this.seatsService.deleteRow(body);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRow(@Valid @RequestBody RowUpdateDTO body) {
        this.seatsService.updateRow(body);

    }

    @PutMapping("/{seatId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateSingleSeat(@Valid @RequestBody UpdateSingleSeatDTO body, @PathVariable UUID seatId) {
        this.seatsService.updateSingleSeat(body, seatId);
    }
}
