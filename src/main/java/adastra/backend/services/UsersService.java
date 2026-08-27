package adastra.backend.services;

import adastra.backend.DTO.EmailUpdateDTO;
import adastra.backend.DTO.PasswordUpdateDTO;
import adastra.backend.DTO.UserRegistrationDTO;
import adastra.backend.DTO.UserUpdateDTO;
import adastra.backend.entities.User;
import adastra.backend.exceptions.NotFoundException;
import adastra.backend.repository.UsersRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UsersService {

    private UsersRepository usersRepository;
    private PasswordEncoder bcrypt;
    private Cloudinary uploader;


    public User save(UserRegistrationDTO body) {
        return this.usersRepository.save(new User(body.name(), body.surname(), body.email(), body.birthDate(), this.bcrypt.encode(body.password())));
    }

    public User findByEmail(String email) {
        return this.usersRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundException("Email non trovata nel database"));
    }

    public User findById(UUID userId) {
        return this.usersRepository.findById(userId).orElseThrow(() -> new NotFoundException("Utente non trovato"));
    }

    public void profileUpdate(UserUpdateDTO body, UUID userId) {
        User found = this.findById(userId);
        found.setBirthDate(body.birthDate());
        found.setName(body.name());
        found.setSurname(body.surname());
        this.usersRepository.save(found);
    }

    public void emailUpdate(EmailUpdateDTO body, UUID userId) {
        User found = this.findById(userId);
        found.setEmail(body.email());
        this.usersRepository.save(found);
    }

    public void passwordUpdate(UUID userId, PasswordUpdateDTO body) throws BadRequestException {
        User found = this.findById(userId);

        if (this.bcrypt.matches(body.oldPassword(), found.getPassword())) {
            found.setPassword(this.bcrypt.encode(body.newPassword()));
        } else throw new BadRequestException("Le password non corrispondono!");
        this.usersRepository.save(found);
    }

    public void findByIdAndDelete(UUID userId) {
        User found = this.findById(userId);
        this.usersRepository.delete(found);
    }

    public void avatarUpdate(User user, MultipartFile file) {

        User found = this.findById(user.getId());

        try {
            Map result = uploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String url = (String) result.get("secure_url");
            found.setProfilePicLink(url);
            this.usersRepository.save(found);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}