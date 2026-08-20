package adastra.backend.services;

import adastra.backend.DTO.ScreenDTO;
import adastra.backend.DTO.ScreenUpdateDTO;
import adastra.backend.entities.Cinema;
import adastra.backend.entities.Screen;
import adastra.backend.enums.EmergencyExits;
import adastra.backend.exceptions.NotFoundException;
import adastra.backend.exceptions.WrongBodyException;
import adastra.backend.repository.ScreensRepository;
import adastra.backend.softDelete.SoftDeleteMethod;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ScreensService extends SoftDeleteMethod<Screen, UUID> {

    private ScreensRepository screensRepository;
    private CinemasService cinemasService;

    @Override
    protected JpaRepository<Screen, UUID> getRepository() {
        return this.screensRepository;
    }

    @Override
    protected String getEntityName() {
        return "sala";
    }

    @Override
    public void softDeleteGeneric(UUID entityId, String body) {
        super.softDeleteGeneric(entityId, body);
    }

    public Screen save(ScreenDTO body) {
        Cinema found = this.cinemasService.findByID(body.cinemaId());
        EmergencyExits exit;

        switch (body.emergencyExit()) {
            case "left" -> exit = EmergencyExits.LEFT;
            case "right" -> exit = EmergencyExits.RIGHT;
            default -> throw new WrongBodyException("Le uscite possono essere solo 'left' o 'right'");
        }

        return this.screensRepository.save(new Screen(body.screenNumber(), found, exit));
    }

    public List<Screen> findAll() {
        return this.screensRepository.findAll();
    }

    public List<Screen> findAllByCinema(UUID cinemaId) {
        Cinema found = this.cinemasService.findByID(cinemaId);
        return this.screensRepository.findScreenByCinemaId(found);
    }

    public void screenUpdate(ScreenUpdateDTO body, UUID screenId) {
        Screen found = this.findById(screenId);
        found.setScreenNumber(body.screenNumber());
        this.screensRepository.save(found);
    }

    public Screen findById(UUID screenId) {
        return this.screensRepository.findById(screenId).orElseThrow(() -> new NotFoundException("Nessun cinema trovato con questo id"));
    }
}
