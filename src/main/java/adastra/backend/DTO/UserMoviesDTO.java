package adastra.backend.DTO;

import java.time.LocalDate;
import java.util.UUID;

public record UserMoviesDTO(String title,
                            LocalDate releaseDate,
                            UUID movieId) {
}
