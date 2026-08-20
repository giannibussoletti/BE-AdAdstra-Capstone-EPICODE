package adastra.backend.DTO;

import java.util.UUID;

public record BookingDTO(
        String guestEmail,
        UUID userId) {
}
