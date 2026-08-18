package adastra.backend.controllers;

import adastra.backend.DTO.ResponseDTO;
import adastra.backend.DTO.ScreeningSeatDTO;
import adastra.backend.entities.ScreeningSeat;
import adastra.backend.services.ScreeningSeatsServices;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/screening-seats")
@AllArgsConstructor
public class ScreeningSeatController {


    private ScreeningSeatsServices screeningSeatsServices;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private ResponseDTO save(@Valid @RequestBody ScreeningSeatDTO body) {
        ScreeningSeat saved = this.screeningSeatsServices.save(body);
        return new ResponseDTO("posto ed orario salvati correttamente", saved.getId(), LocalDateTime.now());
    }
}
