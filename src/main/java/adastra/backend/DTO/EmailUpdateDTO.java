package adastra.backend.DTO;

import jakarta.validation.constraints.Email;

public record EmailUpdateDTO(
        @Email(message = "La mail non rispetta i requisiti necessari")
        String newEmail
) {
}


