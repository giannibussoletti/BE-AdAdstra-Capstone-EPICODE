package adastra.backend.DTO;

import jakarta.validation.constraints.*;

import java.util.List;
import java.util.UUID;

public record SeatDTO(
        @NotNull(message = "il campo non può essere lasciato vuoto")
        Character row,
        @Positive(message = "Il numero deve essere positivo")
        @NotNull(message = "il campo non può essere lasciato vuoto")
        @Min(value = 1, message = "il numero del posto deve essere minimo 1")
        Integer minNumber,
        @Positive(message = "Il numero deve essere positivo")
        @NotNull(message = "il campo non può essere lasciato vuoto")
        @Max(value = 20, message = "il numero del posto può essere massimo 20")
        Integer maxNumber,
        @NotNull(message = "Il campo non può essere lasciato vuoto")
        UUID screenId,
        @NotBlank(message = "Il campo non può essere lasciato vuoto")
        String color,
        @NotNull(message = "Il campo non può essere lasciato vuoto")
        List<String> svgCoordinates
) {
}
