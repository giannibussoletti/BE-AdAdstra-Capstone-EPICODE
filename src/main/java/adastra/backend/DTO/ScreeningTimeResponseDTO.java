package adastra.backend.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record ScreeningTimeResponseDTO(
        @NotBlank(message = "il campo non può essere lasciato vuoto")
        LocalDateTime dateTime,
        @NotNull(message = "il campo non può essere lasciato vuoto")
        UUID screenId,
        @NotNull(message = "il campo non può essere lasciato vuoto")
        Integer screenNumber) {
}
