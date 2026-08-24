package adastra.backend.DTO;

import adastra.backend.entities.Movie;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public record ScreeningTimeMappedDTO(Movie movieDetails,
                                     Map<LocalDate, List<ScreeningTimeResponseDTO>> times) {
}
