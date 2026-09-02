package adastra.backend.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record MovieDTO(
        @NotBlank(message = "Il campo starring non può essere lasciato vuoto")
        String starring,
        @NotNull(message = "Il campo non può essere lasciato vuoto")
//        @FutureOrPresent
        LocalDate releaseDate,
        @NotNull
        @Positive(message = "la duration può essere solo un numero positivo")
        int duration,
        @NotBlank(message = "Il campo title non può essere lasciato vuoto")
        String title,
        @NotBlank(message = "Il campo posterLink non può essere lasciato vuoto")
        String posterLink,
        @NotBlank(message = "Il campo plot non può essere lasciato vuoto")
        String plot,
        String trailer,
        @NotBlank(message = "Il campo plot non può essere lasciato vuoto")
        String director,
        String bannerLink,
        String tagline) {
}

