package adastra.backend.DTO;

import jakarta.validation.constraints.NotBlank;

public record CityDTO(
        @NotBlank(message = "il campo non può essere lasciato vuoto")
        String name) {
}
