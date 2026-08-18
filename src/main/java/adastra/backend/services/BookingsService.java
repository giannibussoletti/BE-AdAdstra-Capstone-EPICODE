package adastra.backend.services;

import adastra.backend.DTO.BookingDTO;
import adastra.backend.entities.Booking;
import adastra.backend.entities.User;
import adastra.backend.repository.BookingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookingsService {

    private BookingRepository bookingRepository;
    private UsersService usersService;


    public Booking save(BookingDTO body) {
        User found = this.usersService.findById(body.userId());

        return this.bookingRepository.save(new Booking(found));
    }
}
