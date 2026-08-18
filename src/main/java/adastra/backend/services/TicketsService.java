package adastra.backend.services;

import adastra.backend.DTO.TicketDTO;
import adastra.backend.entities.Booking;
import adastra.backend.entities.Ticket;
import adastra.backend.repository.TicketsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TicketsService {

    private TicketsRepository ticketsRepository;
    private BookingsService bookingsService;

    public Ticket save(TicketDTO body) {
        Booking foundBooking = this.bookingsService.findById(body.bookingId());
        return this.ticketsRepository.save(new Ticket(body.finalPrice(), body.screeningSeatId(), foundBooking));

    }
}
