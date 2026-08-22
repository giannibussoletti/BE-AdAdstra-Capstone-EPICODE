package adastra.backend.controllers;

import adastra.backend.DTO.LoginDTO;
import adastra.backend.DTO.LoginResponseDTO;
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

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public LoginResponseDTO login(@Valid @RequestBody LoginDTO body) {
        return new LoginResponseDTO(this.loginService.login(body));
    }
}
