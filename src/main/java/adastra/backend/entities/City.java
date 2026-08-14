package adastra.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "cities")
public class City {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private UUID id;

    private String city;


    public City(String city) {
        this.city = city;
    }
}