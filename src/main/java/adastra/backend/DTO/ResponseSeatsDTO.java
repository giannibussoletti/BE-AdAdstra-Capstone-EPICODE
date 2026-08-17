package adastra.backend.DTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public record ResponseSeatsDTO(String message, ArrayList<UUID> ids, LocalDateTime createdAt) {
}
