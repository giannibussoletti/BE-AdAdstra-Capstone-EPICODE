package adastra.backend.services;

import adastra.backend.DTO.EmailUpdateDTO;
import adastra.backend.DTO.PasswordUpdateDTO;
import adastra.backend.DTO.UserRegistrationDTO;
import adastra.backend.entities.User;
import adastra.backend.exceptions.NotFoundException;
import adastra.backend.repository.UsersRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UsersService {

    private UsersRepository usersRepository;
    private PasswordEncoder bcrypt;
    private Cloudinary uploader;


    public User save(UserRegistrationDTO body) {
        return this.usersRepository.save(new User(body.name(), body.surname(), body.email(), LocalDate.parse(body.birthDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")), this.bcrypt.encode(body.password())));
    }

    public User findByEmail(String email) {
        return this.usersRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundException("Email non trovata nel database"));
    }

    public User findById(UUID userId) {
        return this.usersRepository.findById(userId).orElseThrow(() -> new NotFoundException("Utente non trovato"));
    }


    public void emailUpdate(EmailUpdateDTO body, UUID userId) {
        User found = this.findById(userId);
        found.setEmail(body.newEmail());
        this.usersRepository.save(found);
    }

    public void passwordUpdate(UUID userId, PasswordUpdateDTO body) throws BadRequestException {
        User found = this.findById(userId);

        if (!this.bcrypt.matches(body.oldPassword(), found.getPassword())) {
            throw new BadRequestException("La vecchia password non è corretta!");
        }

        if (this.bcrypt.matches(body.newPassword(), found.getPassword())) {
            throw new BadRequestException("La nuova password deve essere diversa dalla vecchia!");
        }

        found.setPassword(this.bcrypt.encode(body.newPassword()));
        this.usersRepository.save(found);
    }

    public void findByIdAndDelete(UUID userId) {
        User found = this.findById(userId);
        this.usersRepository.delete(found);
    }

    @Transactional
    public User avatarUpdate(User user, MultipartFile file) {

        User found = this.findById(user.getId());

        try {
            // Opzioni di caricamento e ottimizzazione
            Map uploadParams = ObjectUtils.asMap(
                    "folder", "avatars",
                    "transformation", new Transformation()
                            .width(400)
                            .height(400)
                            .crop("fill")
                            .gravity("face")
                            .quality("auto:good")
                            .fetchFormat("auto")
            );

            Map result = uploader.uploader().upload(file.getBytes(), uploadParams);
            String url = (String) result.get("secure_url");

            found.setProfilePicLink(url);
            return this.usersRepository.save(found);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}