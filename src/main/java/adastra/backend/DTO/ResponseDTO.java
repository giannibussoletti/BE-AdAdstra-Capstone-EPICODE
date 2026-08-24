package adastra.backend.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

public record ResponseDTO(String message, UUID id, LocalDateTime createdAt) {
}
