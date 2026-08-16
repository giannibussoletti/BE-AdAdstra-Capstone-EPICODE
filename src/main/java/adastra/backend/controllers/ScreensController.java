package adastra.backend.controllers;

import adastra.backend.DTO.*;
import adastra.backend.entities.Screen;
import adastra.backend.services.ScreensService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/screens")
@AllArgsConstructor
public class ScreensController {

    private ScreensService screensService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO save(@Valid @RequestBody ScreenDTO body) {
        Screen saved = this.screensService.save(body);
        return new ResponseDTO("Sala salvata correttamente", saved.getId(), LocalDateTime.now());
    }

    @PatchMapping("/{screenId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateScreen(@Valid @RequestBody ScreenUpdateDTO body, @PathVariable UUID screenId) {
        this.screensService.screenUpdate(body, screenId);
    }

    @PatchMapping("/remove/{screenId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDeleteDTO softDelete(@RequestBody DeleteDTO body, @PathVariable UUID screenId) {
        this.screensService.softDeleteGeneric(screenId, body.deletion());
        return new ResponseDeleteDTO("Stato della sala aggiornato correttamente", LocalDateTime.now());
    }
}
