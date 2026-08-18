package adastra.backend.DTO;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record BookingDTO(
        @NotNull(message = "Il campo non può essere lasciato vuoto")
        UUID userId) {
}
