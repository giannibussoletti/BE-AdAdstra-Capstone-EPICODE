package adastra.backend.DTO;

import java.util.List;
import java.util.UUID;

public record BookingDTO(
        UUID screenTimeId,
        List<BookedSeatsDTO> maxSeats,
        Double totalCost,
        String guestEmail


) {
}
