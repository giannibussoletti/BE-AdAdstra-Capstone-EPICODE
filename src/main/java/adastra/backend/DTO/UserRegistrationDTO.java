package adastra.backend.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRegistrationDTO(
        @NotBlank(message = "Il campo non può essere lasciato vuoto")
        @Size(min = 2, max = 30, message = "il nome deve essere fra i 2 e i 30 caratteri")
        String name,
        @Size(min = 2, max = 30, message = "il cognome deve essere fra i 2 e i 30 caratteri")
        @NotBlank(message = "Il campo non può essere lasciato vuoto")
        String surname,
        @Email(message = "La mail non rispetta i requisiti necessari")
        @NotBlank(message = "Il campo non può essere lasciato vuoto")
        String email,
        @NotBlank(message = "Il campo non può essere lasciato vuoto")
        String birthDate,
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{12,}$",
                message = "La password deve contenere almeno 1 maiuscola, 1 minuscola, 1 numero, un simbolo e deve essere di almeno 12 caratteri")
        @NotBlank(message = "la password è obbligatoria")
        String password
) {
}
