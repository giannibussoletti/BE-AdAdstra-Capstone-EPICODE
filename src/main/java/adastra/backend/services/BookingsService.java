package adastra.backend.services;

import adastra.backend.DTO.BookingDTO;
import adastra.backend.entities.Booking;
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


    public Booking save(BookingDTO body) {
        if (body.userId() != null) {
            User found = this.usersService.findById(body.userId());
            return this.bookingRepository.save(new Booking(found));
        } else {
            return this.bookingRepository.save(new Booking(body.guestEmail()));
        }
    }
    
    public Booking findById(UUID bookingId) {
        return this.bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("Acquisto non trovato non trovato"));
    }
}
