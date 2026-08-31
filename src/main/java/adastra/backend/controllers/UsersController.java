package adastra.backend.controllers;

import adastra.backend.DTO.EmailUpdateDTO;
import adastra.backend.DTO.PasswordUpdateDTO;
import adastra.backend.DTO.ResponseNoIdDTO;
import adastra.backend.entities.User;
import adastra.backend.services.UsersService;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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


    @PatchMapping("profile/new-email")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseNoIdDTO updateMail(@AuthenticationPrincipal User authenticatedUser, @RequestBody EmailUpdateDTO body) {
        this.usersService.emailUpdate(body, authenticatedUser.getId());
        return new ResponseNoIdDTO("newEmail aggiornata correttamente", LocalDateTime.now());

    }

    @PatchMapping("profile/password")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseNoIdDTO updatePassword(@AuthenticationPrincipal User authenticatedUser, @RequestBody PasswordUpdateDTO body) throws BadRequestException {
        this.usersService.passwordUpdate(authenticatedUser.getId(), body);
        return new ResponseNoIdDTO("password aggiornata correttamente", LocalDateTime.now());
    }

    @PostMapping("profile/avatar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAvatar(@AuthenticationPrincipal User authenticatedUser, @RequestParam("avatar_pic") MultipartFile file) {
        this.usersService.avatarUpdate(authenticatedUser, file);
    }

    ;

    @DeleteMapping("/delete")
    public void deleteOwnProfile(@AuthenticationPrincipal User authenticatedUser) {
        this.usersService.findByIdAndDelete(authenticatedUser.getId());
    }


}

