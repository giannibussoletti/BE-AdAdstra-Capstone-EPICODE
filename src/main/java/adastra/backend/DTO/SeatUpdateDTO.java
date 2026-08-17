package adastra.backend.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record SeatUpdateDTO(
        List<UUID> seats,
        @NotNull(message = "il campo non può essere lasciato vuoto")
        Character row,
        @NotNull(message = "Il campo non può essere lasciato vuoto")
        UUID screenId,
        @NotBlank(message = "Il campo non può essere lasciato vuoto")
        String color) {
}
