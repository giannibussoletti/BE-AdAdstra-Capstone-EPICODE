package adastra.backend.services;

import adastra.backend.DTO.LoginDTO;
import adastra.backend.DTO.LoginResponseDTO;
import adastra.backend.DTO.TokenVerificationDTO;
import adastra.backend.entities.User;
import adastra.backend.exceptions.UnauthorizedException;
import adastra.backend.security.JwtTools;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {

    private UsersService usersService;
    private PasswordEncoder bcrypt;
    private JwtTools jwtTools;

    public LoginResponseDTO login(LoginDTO body) {
        User found = this.usersService.findByEmail(body.email());

        if (this.bcrypt.matches(body.password(), found.getPassword())) {
            return new LoginResponseDTO(this.jwtTools.generateToken(found), found.getSurname(), found.getName(), found.getEmail(), found.getBirthDate(), found.getProfilePicLink(), found.getEmail());
        } else {
            throw new UnauthorizedException("Credenziali Sbagliate");
        }
    }

    public void verifyToken(TokenVerificationDTO body) {

        this.jwtTools.verifyToken(body.token());
    }
}
