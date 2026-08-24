package adastra.backend.services;

import adastra.backend.entities.Booking;
import adastra.backend.entities.ScreeningTime;
import adastra.backend.entities.Seat;
import adastra.backend.entities.Ticket;
import adastra.backend.repository.TicketsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TicketsService {

    private TicketsRepository ticketsRepository;
    private ScreeningTimeService screeningTimeService;


    public Ticket save(Booking booking, ScreeningTime time, Seat seat) {
        return this.ticketsRepository.save(new Ticket(booking, seat, time));

    }

    public List<Seat> findSeatByScreeningTime(UUID timeId) {
        ScreeningTime found = this.screeningTimeService.findById(timeId);
        List<Ticket> ticketsFound = this.ticketsRepository.findTicketByScreeningTimeId(found);
        return ticketsFound.stream().map(Ticket::getSeatId).toList();
    }
}
