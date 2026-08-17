package adastra.backend.services;

import adastra.backend.DTO.LoginDTO;
import adastra.backend.entities.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@AllArgsConstructor
public class LoginService {

    private UsersService usersService;

    public User login(@Valid @RequestBody LoginDTO body) {
        return this.usersService.findByEmail(body.email());
    }
}
