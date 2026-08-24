package adastra.backend.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ScreenUpdateDTO(@Positive(message = "il numero della sala può essere solo positivo")
                              @Max(message = "il numero massimo della sala può essere solo 20", value = 20)
                              @NotNull(message = "Il campo non può essere lasciato vuoto")
                              int screenNumber) {
}
