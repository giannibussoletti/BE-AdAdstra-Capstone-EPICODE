package adastra.backend.DTO;

import java.util.UUID;

public record TicketDTO(
        Double price,
        UUID seatId,
        UUID bookingId,
        UUID screeningTime,
        String coupon

) {
}
