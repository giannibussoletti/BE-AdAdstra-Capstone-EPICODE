package adastra.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity

@NoArgsConstructor
@Getter
@Table(name = "tickets", uniqueConstraints = {@UniqueConstraint(columnNames = {"screeningTimeId", "seatId"})}
)

public class Ticket {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private double price;

    @OneToOne
    @JoinColumn(nullable = false, name = "seat_id")
    private Seat seatId;

    @ManyToOne
    @JoinColumn(nullable = false, name = "booking_id")
    private Booking bookingId;

    @ManyToOne
    @JoinColumn(nullable = false, name = "screening_time_id")
    private ScreeningTime screeningTimeId;

    private String coupon;

    public Ticket(double price, Booking bookingId, Seat seatId, ScreeningTime screeningTimeId) {
        this.price = price;
        this.bookingId = bookingId;
        this.seatId = seatId;
        this.screeningTimeId = screeningTimeId;
    }

    public Ticket(double price, Booking bookingId, Seat seatId, ScreeningTime screeningTimeId, String coupon) {
        this.price = price;
        this.bookingId = bookingId;
        this.seatId = seatId;
        this.screeningTimeId = screeningTimeId;
        this.coupon = coupon;
    }
}
