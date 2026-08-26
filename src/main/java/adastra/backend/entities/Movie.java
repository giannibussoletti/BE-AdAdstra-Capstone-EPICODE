package adastra.backend.entities;

import adastra.backend.enums.IsDeleted;
import adastra.backend.softDelete.SoftDeleteInt;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "movies")
@Getter
@NoArgsConstructor
@Setter
public class Movie implements SoftDeleteInt {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String starring;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, name = "movie_is_deleted")
    @Enumerated(EnumType.STRING)
    private IsDeleted isDeleted;

    @Column(nullable = false, name = "poster_link", columnDefinition = "TEXT")
    private String posterLink;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String plot;

    @Column(nullable = false, name = "release_date")
    private LocalDate releaseDate;

    public Movie(String starring, int duration, String title, String posterLink, String plot, LocalDate releaseDate) {

        this.starring = starring;
        this.duration = duration;
        this.title = title;
        this.posterLink = posterLink;
        this.plot = plot;
        this.isDeleted = IsDeleted.FALSE;
        this.releaseDate = releaseDate;
    }
}

