package adastra.backend.controllers;

import adastra.backend.DTO.*;
import adastra.backend.entities.User;
import adastra.backend.services.UsersService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UsersController {

    private UsersService usersService;

    @GetMapping("/profile")
    public User userProfile(@AuthenticationPrincipal User authenticatedUser) {
        return authenticatedUser;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO saveUser(@Valid @RequestBody UserRegistrationDTO body) {
        User saved = this.usersService.save(body);
        return new ResponseDTO("Registrazione avvenuta con successo", saved.getId(), LocalDateTime.now());
    }


    @PutMapping("/profile/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseNoIdDTO updateProfile(@AuthenticationPrincipal User authenticatedUser, @RequestBody UserUpdateDTO body) {

        this.usersService.profileUpdate(body, authenticatedUser.getId());
        return new ResponseNoIdDTO("Profilo aggiornato con successo", LocalDateTime.now());
    }

    @PatchMapping("/profile/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseNoIdDTO updateMail(@AuthenticationPrincipal User authenticatedUser, @RequestBody EmailUpdateDTO body) {
        this.usersService.emailUpdate(body, authenticatedUser.getId());
        return new ResponseNoIdDTO("email aggiornata", LocalDateTime.now());

    }


    @DeleteMapping("/delete")
    public void deleteOwnProfile(@AuthenticationPrincipal User authenticatedUser) {
        this.usersService.findByIdAndDelete(authenticatedUser.getId());
    }


}

