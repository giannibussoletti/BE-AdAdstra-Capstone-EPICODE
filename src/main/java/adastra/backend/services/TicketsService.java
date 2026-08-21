package adastra.backend.services;

import adastra.backend.entities.Booking;
import adastra.backend.entities.ScreeningTime;
import adastra.backend.entities.Seat;
import adastra.backend.entities.Ticket;
import adastra.backend.repository.TicketsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TicketsService {

    private TicketsRepository ticketsRepository;
    private BookingsService bookingsService;
    private ScreeningTimeService screeningTimeService;
    private SeatsService seatsService;


    public Ticket save(Booking booking, ScreeningTime time, Seat seat) {
        return this.ticketsRepository.save(new Ticket(booking, seat, time));


    }
}
