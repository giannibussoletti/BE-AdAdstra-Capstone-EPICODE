package adastra.backend.entities;

import adastra.backend.enums.SeatStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Seat {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private char row;

    @Column(nullable = false)
    private int number;

    @Column(nullable = false, name = "svg_coordinates")
    private String svgCoordinates;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "seat_screen",
            joinColumns = @JoinColumn(name = "seat_id"),
            inverseJoinColumns = @JoinColumn(name = "screen_id"))
    private List<Screen> screenIds;


    @Column(nullable = false)
    private String color;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatStatus seatStatus;


    public Seat(String color, char row, int number, String svgCoordinates, List<Screen> screenIds) {
        this.row = row;
        this.number = number;
        this.screenIds = screenIds;
        this.color = color;
        this.svgCoordinates = svgCoordinates;
        this.seatStatus = SeatStatus.OK;


    }

}
