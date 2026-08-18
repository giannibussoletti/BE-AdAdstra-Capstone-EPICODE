package adastra.backend.DTO;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ScreeningSeatDTO(
        @NotNull(message = "Il campo non può essere lasciato vuoto")
        UUID seatId,
        @NotNull(message = "Il campo non può essere lasciato vuoto")
        UUID screeningTimeId,
        @NotNull(message = "Il campo non può essere lasciato vuoto")
        Double price
) {
}
