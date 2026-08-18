package adastra.backend.entities;

import adastra.backend.enums.IsDeleted;
import adastra.backend.softDelete.SoftDeleteInt;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "cinemas")
public class Cinema implements SoftDeleteInt {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(name = "cinema_name", nullable = false)
    private String cinemaName;

    @Column(nullable = false)
    private String address;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    @JsonIgnore
    private City cityId;

    @Column(nullable = false, name = "cinema_is_deleted")
    @Enumerated(EnumType.STRING)
    private IsDeleted isDeleted;

    public Cinema(String cinemaName, String address, City cityId) {
        this.cinemaName = cinemaName;
        this.address = address;
        this.cityId = cityId;
        this.isDeleted = IsDeleted.FALSE;
    }
}
