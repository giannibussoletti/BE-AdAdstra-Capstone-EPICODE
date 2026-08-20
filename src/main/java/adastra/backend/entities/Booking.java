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

    public Booking(String guestMail) {
        this.createdAt = LocalDateTime.now();
        this.guestMail = guestMail;
    }

    public Booking(User userId) {
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
    }
}
