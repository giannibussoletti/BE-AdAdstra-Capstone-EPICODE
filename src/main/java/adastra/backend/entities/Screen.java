package adastra.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "cinemas")
public class Screen {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private UUID id;

    @Column(name = "screen_number", nullable = false)
    private Integer screenNumber;

    @ManyToOne
    @JoinColumn(name = "cinema_id", nullable = false)
    private Cinema cinemaId;

    public Screen(Integer screenNumber, Cinema cinemaId) {
        this.screenNumber = screenNumber;
        this.cinemaId = cinemaId;
    }
}
