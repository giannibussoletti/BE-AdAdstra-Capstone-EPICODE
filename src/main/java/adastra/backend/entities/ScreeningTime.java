package adastra.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "screening_time")
@NoArgsConstructor
@Getter
public class ScreeningTime {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false, name = "date_time")
    private LocalDateTime dateTime;
    
    @ManyToOne
    @JoinColumn(nullable = false, name = "movie_id")
    private Movie movieId;

    @ManyToOne
    @JoinColumn(nullable = false, name = "screen_id")
    private Screen screenId;

    public ScreeningTime(LocalDateTime dateTime, Movie movieId, Screen screenId) {
        this.dateTime = dateTime;
        this.movieId = movieId;
        this.screenId = screenId;
    }
}
