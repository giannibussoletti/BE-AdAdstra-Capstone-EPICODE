package adastra.backend.DTO;

import java.util.UUID;

public record TicketDTO(
        UUID seatId,
        UUID bookingId,
        UUID screeningTime,
        String coupon

) {
}
