package adastra.backend.controllers;

import adastra.backend.DTO.LoginDTO;
import adastra.backend.entities.User;
import adastra.backend.services.LoginService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class LoginController {

    private LoginService loginService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public User login(@Valid @RequestBody LoginDTO body) {
        return this.loginService.login(body);
    }
}
