package adastra.backend.entities;

import adastra.backend.softDeletion.SoftDeleteInt;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "cinemas")
public class Cinema implements SoftDeleteInt {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private UUID id;

    @Column(name = "cinema_name", nullable = false)
    private String cinemaName;

    @Column(nullable = false)
    private String address;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City cityId;

    public Cinema(String cinemaName, String address, City cityId) {
        this.cinemaName = cinemaName;
        this.address = address;
        this.cityId = cityId;
    }
}
