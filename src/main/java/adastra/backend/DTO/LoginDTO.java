package adastra.backend.DTO;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @NotBlank(message = "il campo email non può essere vuoto")
        String email,
        @NotBlank(message = "il campo password non può essere vuoto")
        String password) {
}
