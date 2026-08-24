package adastra.backend.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ScreeningTimeDTO(
        @NotBlank(message = "il campo non può essere lasciato vuoto")
        String dateTime,
        @NotNull(message = "il campo non può essere lasciato vuoto")
        UUID movieId,
        @NotNull(message = "il campo non può essere lasciato vuoto")
        UUID screenId) {
}
