package adastra.backend.services;

import adastra.backend.DTO.BookingDTO;
import adastra.backend.entities.Booking;
import adastra.backend.entities.ScreeningTime;
import adastra.backend.entities.Seat;
import adastra.backend.entities.User;
import adastra.backend.exceptions.NotFoundException;
import adastra.backend.repository.BookingRepository;
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
    private ScreensService screensService;
    private SeatsService seatsService;


//    UUID userId,
//    UUID screenTimeId,
//    UUID screenId,
//    List<BookedSeatsDTO> maxSeats,
//    Double totalCost,
//    String guestEmail

    public Booking save(BookingDTO body, UUID authUser) {
        Booking booking = null;
        if (authUser != null) {
            User found = this.usersService.findById(authUser);
            booking = this.bookingRepository.save(new Booking(found, body.totalCost()));
        } else {
            booking = this.bookingRepository.save(new Booking(body.guestEmail(), body.totalCost()));
        }
        Booking finalBooking = booking;
        ScreeningTime time = this.screeningTimeService.findById(body.screenTimeId());

        body.maxSeats().forEach(seat -> {
            Seat seatFound = this.seatsService.findById(seat.id());
            this.ticketsService.save(finalBooking, time, seatFound);
        });

        return finalBooking;


    }

    public Booking findById(UUID bookingId) {
        return this.bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("Acquisto non trovato non trovato"));
    }
}
