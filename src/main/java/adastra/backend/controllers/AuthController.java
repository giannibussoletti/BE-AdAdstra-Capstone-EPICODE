package adastra.backend.controllers;

import adastra.backend.DTO.LoginDTO;
import adastra.backend.DTO.LoginResponseDTO;
import adastra.backend.DTO.ResponseDTO;
import adastra.backend.DTO.UserRegistrationDTO;
import adastra.backend.entities.User;
import adastra.backend.services.LoginService;
import adastra.backend.services.UsersService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private LoginService loginService;
    private UsersService usersService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponseDTO login(@Valid @RequestBody LoginDTO body) {
        return new LoginResponseDTO(this.loginService.login(body));
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO saveUser(@Valid @RequestBody UserRegistrationDTO body) {
        User saved = this.usersService.save(body);
        return new ResponseDTO("Registrazione avvenuta con successo", saved.getId(), LocalDateTime.now());
    }
}
