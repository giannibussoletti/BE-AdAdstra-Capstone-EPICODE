package adastra.backend.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserMoviesDTO(String title,
                            UUID movieId,
                            String seat,
                            LocalDateTime screeningDate,
                            Integer theater) {
}
