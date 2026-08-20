package adastra.backend.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record ScreenDTO(
        @NotBlank(message = "Il campo non può essere lasciato vuoto")
        String emergencyExit,
        @Positive(message = "il numero della sala può essere solo positivo")
        @Max(message = "il numero massimo della sala può essere solo 20", value = 20)
        @NotNull(message = "Il campo non può essere lasciato vuoto")
        int screenNumber,
        @NotNull(message = "il campo non può essere lasciato vuoto")
        UUID cinemaId) {
}
