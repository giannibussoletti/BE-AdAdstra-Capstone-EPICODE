package adastra.backend.controllers;

import adastra.backend.DTO.ResponseDTO;
import adastra.backend.DTO.UserDTO;
import adastra.backend.entities.User;
import adastra.backend.services.UsersService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UsersController {

    private UsersService usersService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO saveUser(@Valid @RequestBody UserDTO body) {
        User saved = this.usersService.save(body);
        return new ResponseDTO("Registrazione avvenuta con successo", saved.getId(), LocalDateTime.now());
    }
}
