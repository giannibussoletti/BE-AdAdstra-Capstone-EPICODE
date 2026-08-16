package adastra.backend.entities;

import adastra.backend.enums.IsDeleted;
import adastra.backend.softDeletion.SoftDeleteInt;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "screens")
public class Screen implements SoftDeleteInt {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(name = "screen_number", nullable = false)
    private int screenNumber;

    @ManyToOne
    @JoinColumn(name = "cinema_id", nullable = false)
    private Cinema cinemaId;

    @Column(nullable = false, name = "screen_is_deleted")
    @Enumerated(EnumType.STRING)
    private IsDeleted isDeleted;

    public Screen(int screenNumber, Cinema cinemaId) {
        this.screenNumber = screenNumber;
        this.cinemaId = cinemaId;
        this.isDeleted = IsDeleted.FALSE;
    }
}
