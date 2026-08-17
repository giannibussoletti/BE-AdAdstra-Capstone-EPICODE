package adastra.backend.entities;

import adastra.backend.enums.IsDeleted;
import adastra.backend.enums.UserRole;
import adastra.backend.softDelete.SoftDeleteInt;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User implements SoftDeleteInt {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, name = "birth_date")
    private LocalDate birthDate;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "TEXT", name = "profile_pic_link")
    private String profilePicLink;

    @Column(nullable = false, name = "user_role")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column(nullable = false, name = "user_is_deleted")
    @Enumerated(EnumType.STRING)
    private IsDeleted isDeleted;

    public User(String name, String surname, String email,
                LocalDate birthDate,
                String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.birthDate = birthDate;
        this.password = password;
        this.profilePicLink = "https://placehold.co/100";
        this.userRole = UserRole.USER;
        this.isDeleted = IsDeleted.FALSE;
    }
}
