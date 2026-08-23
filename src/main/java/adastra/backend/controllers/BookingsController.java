package adastra.backend.controllers;

import adastra.backend.DTO.BookingDTO;
import adastra.backend.DTO.ResponseDTO;
import adastra.backend.entities.Booking;
import adastra.backend.entities.User;
import adastra.backend.services.BookingsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/bookings")
@AllArgsConstructor
public class BookingsController {

    private BookingsService bookingsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO save(@AuthenticationPrincipal User authUser, @Valid @RequestBody BookingDTO body) {
        Booking saved = this.bookingsService.save(body, authUser.getId());

        return new ResponseDTO("Acquisto avvenuto con successo", saved.getId(), LocalDateTime.now());
    }
}
