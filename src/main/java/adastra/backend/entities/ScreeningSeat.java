package adastra.backend.entities;

import adastra.backend.enums.ScreenSeatStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Table(
        name = "screening_seats",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"screeningTimeId", "seatId"})
        }
)
public class ScreeningSeat {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private UUID id;
    @ManyToOne
    @JoinColumn(nullable = false, name = "seat_id")
    private Seat seatId;
    @ManyToOne
    @JoinColumn(nullable = false, name = "screening_time_id")
    private ScreeningTime screeningTimeId;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ScreenSeatStatus status;
    @Column(nullable = false)
    private Double price;

    public ScreeningSeat(Seat seatId, ScreeningTime screeningTimeId, ScreenSeatStatus status, Double price) {
        this.seatId = seatId;
        this.screeningTimeId = screeningTimeId;
        this.status = status;
        this.price = price;
    }
}