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
@NoArgsConstructor
@Setter
@Table(name = "cities")
public class City implements SoftDeleteInt {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(nullable = false, unique = true)

    private String city;

    @Column(nullable = false, name = "city_is_deleted")
    @Enumerated(EnumType.STRING)
    private IsDeleted isDeleted;

    public City(String city) {
        this.city = city;
        this.isDeleted = IsDeleted.FALSE;
    }
}