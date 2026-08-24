package adastra.backend.entities;

import adastra.backend.enums.IsDeleted;
import adastra.backend.softDelete.SoftDeleteInt;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "screening_time")
@NoArgsConstructor
@Getter
@Setter
public class ScreeningTime implements SoftDeleteInt {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(nullable = false, name = "date_time")
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(nullable = false, name = "movie_id")
    @JsonIgnore
    private Movie movieId;

    @ManyToOne
    @JoinColumn(nullable = false, name = "screen_id")
    @JsonIgnore
    private Screen screenId;

    @Column(nullable = false, name = "screening_time_is_deleted")
    @Enumerated(EnumType.STRING)
    private IsDeleted isDeleted;

    public ScreeningTime(LocalDateTime dateTime, Movie movieId, Screen screenId) {
        this.dateTime = dateTime;
        this.movieId = movieId;
        this.screenId = screenId;
        this.isDeleted = IsDeleted.FALSE;
    }
}
