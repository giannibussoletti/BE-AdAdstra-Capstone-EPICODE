package adastra.backend.DTO;

import java.util.UUID;

public record TicketDTO(
        double finalPrice,
        UUID screeningSeatId,
        UUID bookingId
) {
}
