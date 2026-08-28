package adastra.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity

@NoArgsConstructor
@Getter
@Table(name = "tickets", uniqueConstraints = {@UniqueConstraint(columnNames = {"screening_time_id", "seat_id"})}
)

public class Ticket {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private UUID id;


    @ManyToOne
    @JoinColumn(nullable = false, name = "seat_id")
    private Seat seatId;

    @ManyToOne
    @JoinColumn(nullable = false, name = "booking_id")
    private Booking bookingId;

    @ManyToOne
    @JoinColumn(nullable = false, name = "screening_time_id")
    private ScreeningTime screeningTimeId;


    public Ticket(Booking bookingId, Seat seatId, ScreeningTime screeningTimeId) {
        this.bookingId = bookingId;
        this.seatId = seatId;
        this.screeningTimeId = screeningTimeId;
    }

    public Ticket(Booking bookingId, Seat seatId, ScreeningTime screeningTimeId, String coupon) {
        this.bookingId = bookingId;
        this.seatId = seatId;
        this.screeningTimeId = screeningTimeId;
    }
}
