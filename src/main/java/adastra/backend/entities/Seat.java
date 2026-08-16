package adastra.backend.entities;

import adastra.backend.enums.IsDeleted;
import adastra.backend.softDelete.SoftDeleteInt;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "seats")
public class Seat implements SoftDeleteInt {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(nullable = false)
    private UUID id;
    @Column(nullable = false)
    private char row;
    @Column(nullable = false)
    private int number;
    @ManyToOne
    @JoinColumn(nullable = false, name = "screen_id")
    private Screen screenId;
    @Column(nullable = false)
    private String color;

    @Column(nullable = false, name = "seat_is_deleted")
    @Enumerated(EnumType.STRING)
    private IsDeleted isDeleted;


    public Seat(char row, int number, Screen screenId, String color) {
        this.row = row;
        this.number = number;
        this.screenId = screenId;
        this.color = color;
        this.isDeleted = IsDeleted.FALSE;
    }

}
