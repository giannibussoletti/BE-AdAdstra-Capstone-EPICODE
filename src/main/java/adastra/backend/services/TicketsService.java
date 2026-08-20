package adastra.backend.services;

import adastra.backend.DTO.TicketDTO;
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


    public Ticket save(TicketDTO body) {
        Booking foundBooking = this.bookingsService.findById(body.bookingId());
        ScreeningTime foundTime = this.screeningTimeService.findById(body.screeningTime());
        Seat foundSeat = this.seatsService.findById(body.seatId());
        if (body.coupon().isEmpty() || body.coupon().isBlank()) {
            return this.ticketsRepository.save(new Ticket(body.price(), foundBooking, foundSeat, foundTime));
        }

        return this.ticketsRepository.save(new Ticket(body.price(), foundBooking, foundSeat, foundTime, body.coupon()));


    }
}
