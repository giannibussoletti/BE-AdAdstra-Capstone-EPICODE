package adastra.backend.DTO;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record UpdateSingleSeatDTO(
        @NotNull(message = "il campo non può essere lasciato vuoto")
        Character row,
        @Positive(message = "Il numero deve essere positivo")
        @NotNull(message = "il campo non può essere lasciato vuoto")
        @Min(value = 1, message = "il numero del posto deve essere minimo 1")
        @Max(value = 20, message = "il numero del posto può essere massimo 20")
        Integer number,
        @NotNull(message = "Il campo non può essere lasciato vuoto")
        UUID screenId,
        @NotBlank(message = "Il campo non può essere lasciato vuoto")
        String color) {
}
