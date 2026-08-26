package adastra.backend.DTO;

import java.time.LocalDate;

public record LoginResponseDTO(String accessToken,
                               String surname,
                               String name,
                               String email,
                               LocalDate birthDate,
                               String profilePicLink,
                               String username) {
}
