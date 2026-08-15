package adastra.backend.entities;

import adastra.backend.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
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
    }
}
