package adastra.backend.services;

import adastra.backend.DTO.EmailAndPasswordUpdateDTO;
import adastra.backend.DTO.UserRegistrationDTO;
import adastra.backend.DTO.UserUpdateDTO;
import adastra.backend.entities.User;
import adastra.backend.exceptions.NotFoundException;
import adastra.backend.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UsersService {

    private UsersRepository usersRepository;


    public User save(UserRegistrationDTO body) {
        return this.usersRepository.save(new User(body.name(), body.surname(), body.email(), body.birthDate(), body.password()));
    }

    public User findByEmail(String email) {
        return this.usersRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundException("Email non trovata nel database"));
    }

    public User findById(UUID userId) {
        return this.usersRepository.findById(userId).orElseThrow(() -> new NotFoundException("Utente non trovato"));
    }

    public void profileUpdate(UserUpdateDTO body, UUID userId) {
        User found = this.findById(userId);
        found.setBirthDate(body.birthDate());
        found.setEmail(body.email());
        found.setName(body.name());
        found.setSurname(body.surname());
        this.usersRepository.save(found);
    }

    public void emailAndPasswordUpdate(EmailAndPasswordUpdateDTO body, UUID userId) {

        User found = this.findById(userId);

        if (body.email() != null && !body.email().isBlank()) {
            found.setEmail(body.email());
        }
        if (body.password() != null && !body.password().isBlank()) {
            found.setPassword(body.password());
        }
        this.usersRepository.save(found);
    }
}
