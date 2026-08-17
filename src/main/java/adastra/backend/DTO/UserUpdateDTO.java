package adastra.backend.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UserUpdateDTO(
        @NotBlank(message = "Il campo non può essere lasciato vuoto")
        @Size(min = 2, max = 30, message = "il nome deve essere fra i 2 e i 30 caratteri")
        String name,
        @Size(min = 2, max = 30, message = "il cognome deve essere fra i 2 e i 30 caratteri")
        @NotBlank(message = "Il campo non può essere lasciato vuoto")
        String surname,
        @Email(message = "La mail non rispetta i requisiti necessari")
        @NotBlank(message = "Il campo non può essere lasciato vuoto")
        String email,
        @JsonFormat(pattern = "dd/MM/yyyy")
        @Past
        LocalDate birthDate
) {
}
