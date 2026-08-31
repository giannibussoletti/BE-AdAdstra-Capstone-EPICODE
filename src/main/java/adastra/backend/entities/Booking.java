package adastra.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "bookings")
@NoArgsConstructor
@Getter
public class Booking {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name = "guest_mail")
    private String guestMail;

    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Double price;

    private String coupon;

    public Booking(String guestMail, Double price) {
        this.createdAt = LocalDateTime.now();
        this.guestMail = guestMail;
        this.price = price;
    }

    public Booking(User userId, Double price, String guestMail) {
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
        this.price = price;
        this.guestMail = guestMail;
    }

    public Booking(String guestMail, Double price, String coupon) {
        this.createdAt = LocalDateTime.now();
        this.guestMail = guestMail;
        this.price = price;
        this.coupon = coupon;
    }

    public Booking(User userId, Double price, String coupon, String guestMail) {
        this.guestMail = guestMail;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
        this.price = price;
        this.coupon = coupon;
    }
}
