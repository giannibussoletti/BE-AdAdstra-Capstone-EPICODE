package adastra.backend.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EmailAndPasswordUpdateDTO(
        @Pattern(regexp = "^(?=.{12,}$)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).*$",
                message = "La password deve contenere almeno 1 maiuscola, 1 minuscola, 1 numero, un simbolo e deve essere di almeno 12 caratteri")
        String password,
        @Email(message = "La mail non rispetta i requisiti necessari")
        String email
) {
}
