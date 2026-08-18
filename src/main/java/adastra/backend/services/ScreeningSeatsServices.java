package adastra.backend.services;

import adastra.backend.DTO.ScreeningSeatDTO;
import adastra.backend.entities.ScreeningSeat;
import adastra.backend.entities.ScreeningTime;
import adastra.backend.entities.Seat;
import adastra.backend.exceptions.NotFoundException;
import adastra.backend.repository.ScreeningSeatsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ScreeningSeatsServices {

    private ScreeningSeatsRepository screeningSeatsRepository;
    private SeatsService seatsService;
    private ScreeningTimeService screeningTimeService;

    public ScreeningSeat save(ScreeningSeatDTO body) {
        Seat foundSeat = this.seatsService.findById(body.seatId());
        ScreeningTime foundTime = this.screeningTimeService.findById(body.screeningTimeId());
        return this.screeningSeatsRepository.save(new ScreeningSeat(foundSeat, foundTime, body.price()));
    }


    public ScreeningSeat findById(UUID screenSeatId) {
        return this.screeningSeatsRepository.findById(screenSeatId).orElseThrow(() -> new NotFoundException("Nessun cinema trovato con questo id"));
    }
}
