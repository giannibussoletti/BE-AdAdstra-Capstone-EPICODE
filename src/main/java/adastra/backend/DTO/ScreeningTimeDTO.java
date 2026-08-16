package adastra.backend.DTO;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record ScreeningTimeDTO(
        @Future(message = "la data deve essere successiva ad oggi")
        @NotNull(message = "il campo non può essere lasciato vuoto")
        LocalDateTime dateTime,
        @NotNull(message = "il campo non può essere lasciato vuoto")
        UUID movieId,
        @NotNull(message = "il campo non può essere lasciato vuoto")
        UUID screenId) {
}
