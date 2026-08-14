package adastra.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "tickets")
@NoArgsConstructor
@Getter

public class Ticket {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false, name = "final_price")
    private Double finalPrice;

    @OneToOne
    @JoinColumn(nullable = false, name = "screening_seat_id")
    private ScreeningSeat screeningSeatId;

    @ManyToOne
    @JoinColumn(nullable = false, name = "booking_id")
    private Booking bookingId;

    public Ticket(Double finalPrice, ScreeningSeat screeningSeatId, Booking bookingId) {
        this.finalPrice = finalPrice;
        this.screeningSeatId = screeningSeatId;
        this.bookingId = bookingId;
    }
}
