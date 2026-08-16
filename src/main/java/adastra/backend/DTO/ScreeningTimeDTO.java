package adastra.backend.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

public record ScreeningTimeDTO(LocalDateTime dateTime,
                               UUID movieId,
                               UUID screenId) {
}
