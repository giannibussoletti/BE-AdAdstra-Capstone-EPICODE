package adastra.backend.controllers;

import adastra.backend.DTO.CityDTO;
import adastra.backend.DTO.DeleteDTO;
import adastra.backend.DTO.ResponseDTO;
import adastra.backend.DTO.ResponseDeleteDTO;
import adastra.backend.entities.City;
import adastra.backend.services.CitiesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/cities")
@AllArgsConstructor
public class CitiesController {

    private CitiesService citiesService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO save(@RequestBody CityDTO body) {
        City saved = this.citiesService.save(body);
        return new ResponseDTO("Città salvata correttamente", saved.getId(), LocalDateTime.now());
    }

    @PatchMapping("/{cityId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDeleteDTO softDelete(@PathVariable UUID cityId, @RequestBody DeleteDTO body) {
        this.citiesService.softDeleteGeneric(cityId, body.deletion());

        return new ResponseDeleteDTO("stato della città aggiornato correttamente", LocalDateTime.now());
    }
}
