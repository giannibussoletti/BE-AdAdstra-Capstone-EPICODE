package adastra.backend.DTO;

import java.util.List;
import java.util.UUID;

public record DeleteRowDTO(String deletion, List<UUID> seats) {
}
