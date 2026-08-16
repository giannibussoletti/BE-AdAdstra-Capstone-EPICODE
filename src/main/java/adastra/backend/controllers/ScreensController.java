package adastra.backend.controllers;

import adastra.backend.DTO.ResponseDTO;
import adastra.backend.DTO.ScreenDTO;
import adastra.backend.entities.Screen;
import adastra.backend.services.ScreensService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/screens")
@AllArgsConstructor
public class ScreensController {

    private ScreensService screensService;

    public ResponseDTO save(@Valid @RequestBody ScreenDTO body) {
        Screen saved = this.screensService.save(body);
        return new ResponseDTO("Sala salvata correttamente", saved.getId(), LocalDateTime.now());
    }
}
