package adastra.backend.controllers;

import adastra.backend.DTO.*;
import adastra.backend.entities.User;
import adastra.backend.services.UsersService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UsersController {

    private UsersService usersService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO saveUser(@Valid @RequestBody UserRegistrationDTO body) {
        User saved = this.usersService.save(body);
        return new ResponseDTO("Registrazione avvenuta con successo", saved.getId(), LocalDateTime.now());
    }

    @GetMapping("/profile/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public User userProfile(@PathVariable UUID userId) {
        return this.usersService.findById(userId);
    }

    @PutMapping("/profile/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseNoIdDTO updateProfile(@Valid @RequestBody UserUpdateDTO body, @PathVariable UUID userId) {

        this.usersService.profileUpdate(body, userId);
        return new ResponseNoIdDTO("Profilo aggiornato con successo", LocalDateTime.now());
    }

    @PatchMapping("/profile/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseNoIdDTO updateMail(@Valid @RequestBody EmailAndPasswordUpdateDTO body, @PathVariable UUID userId) {
        this.usersService.emailAndPasswordUpdate(body, userId);

        if (body.email() != null && !body.email().isBlank()) {
            return new ResponseNoIdDTO("Email aggiornata con successo", LocalDateTime.now());
        }
        if (body.password() != null && !body.password().isBlank()) {
            return new ResponseNoIdDTO("Password aggiornata con successo", LocalDateTime.now());
        }

        return new ResponseNoIdDTO("Nessun aggiornamento effettuato", LocalDateTime.now());

    }
}

