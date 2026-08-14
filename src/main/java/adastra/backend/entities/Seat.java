package adastra.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "seats")
public class Seat {

    @Id
    @GeneratedValue
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


    public Seat(char row, int number, Screen screenId, String color) {
        this.row = row;
        this.number = number;
        this.screenId = screenId;
        this.color = color;
    }

}
