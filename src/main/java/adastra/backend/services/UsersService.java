package adastra.backend.services;

import adastra.backend.DTO.UserDTO;
import adastra.backend.entities.User;
import adastra.backend.exceptions.NotFoundException;
import adastra.backend.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsersService {

    private UsersRepository usersRepository;


    public User save(UserDTO body) {
        return this.usersRepository.save(new User(body.name(), body.surname(), body.email(), body.birthDate(), body.password()));
    }

    public User findByEmail(String email) {
        return this.usersRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundException("Email non trovata nel database"));
    }
}
