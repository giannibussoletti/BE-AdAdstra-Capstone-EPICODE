package adastra.backend.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CinemaDTO(
        @NotBlank(message = "Il campo non può essere lasciato vuoto")
        @Size(min = 2, max = 50, message = "il nome del cinema deve essere compreso fra i 2 e i 50 caratteri")
        String cinemaName,
        @NotBlank(message = "Il campo non può essere lasciato vuoto")
        String address,
        @NotNull(message = "Il campo non può essere lasciato vuoto")
        UUID cityId) {
}
