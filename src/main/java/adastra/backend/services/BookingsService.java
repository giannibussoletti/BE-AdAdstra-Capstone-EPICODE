package adastra.backend.services;

import adastra.backend.DTO.BookingDTO;
import adastra.backend.entities.Booking;
import adastra.backend.entities.ScreeningTime;
import adastra.backend.entities.Seat;
import adastra.backend.entities.User;
import adastra.backend.exceptions.NotFoundException;
import adastra.backend.repository.BookingRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class BookingsService {

    private BookingRepository bookingRepository;
    private UsersService usersService;
    private TicketsService ticketsService;
    private ScreeningTimeService screeningTimeService;
    private SeatsService seatsService;

    @Transactional
    public Booking saveLoggedUser(BookingDTO body, User authUser) {
        Booking booking;
        User found = this.usersService.findById(authUser.getId());
        if (body.coupon().isEmpty() || body.coupon().isBlank())
            booking = this.bookingRepository.save(new Booking(found, body.totalCost(), body.guestEmail()));
        else {
            booking = this.bookingRepository.save(new Booking(found, body.totalCost(), body.coupon(), body.guestEmail()));
        }
        createTicket(booking, body);

        return booking;
    }

    @Transactional
    public Booking savePublic(BookingDTO body) {
        Booking booking;
        if (body.coupon().isEmpty() || body.coupon().isBlank())
            booking = this.bookingRepository.save(new Booking(body.guestEmail(), body.totalCost()));
        else {
            booking = this.bookingRepository.save(new Booking(body.guestEmail(), body.totalCost(), body.coupon()));
        }
        createTicket(booking, body);

        return booking;

    }

    private void createTicket(Booking booking, BookingDTO body) {
        ScreeningTime time = this.screeningTimeService.findById(body.screenTimeId());

        body.maxSeats().forEach(seat -> {
            Seat seatFound = this.seatsService.findById(seat.id());
            this.ticketsService.save(booking, time, seatFound);
        });
    }

    public Booking findById(UUID bookingId) {
        return this.bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("Acquisto non trovato non trovato"));
    }
}
