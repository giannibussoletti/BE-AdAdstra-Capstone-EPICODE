package adastra.backend.DTO;

import jakarta.validation.constraints.Pattern;

public record PasswordUpdateDTO(String oldPassword,
                                @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{12,}$",
                                        message = "La password deve contenere almeno 1 maiuscola, 1 minuscola, 1 numero, un simbolo e deve essere di almeno 12 caratteri")
                                String newPassword) {
}
