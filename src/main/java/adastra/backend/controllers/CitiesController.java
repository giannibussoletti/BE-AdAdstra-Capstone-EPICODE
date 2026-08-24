package adastra.backend.controllers;

import adastra.backend.DTO.CityDTO;
import adastra.backend.DTO.DeleteDTO;
import adastra.backend.DTO.ResponseDTO;
import adastra.backend.DTO.ResponseNoIdDTO;
import adastra.backend.entities.City;
import adastra.backend.services.CitiesService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cities")
@AllArgsConstructor
public class CitiesController {

    private CitiesService citiesService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<City> findAllCities() {
        return this.citiesService.findAllCities();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO save(@Valid @RequestBody CityDTO body) {
        City saved = this.citiesService.save(body);
        return new ResponseDTO("Città salvata correttamente", saved.getId(), LocalDateTime.now());
    }

    @PatchMapping("/delete/{cityId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseNoIdDTO softDelete(@PathVariable UUID cityId, @RequestBody DeleteDTO body) {
        this.citiesService.softDeleteGeneric(cityId, body.deletion());

        return new ResponseNoIdDTO("stato della città aggiornato correttamente", LocalDateTime.now());
    }

    @PatchMapping("/{cityId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseNoIdDTO nameUpdate(@PathVariable UUID cityId, @Valid @RequestBody CityDTO body) {
        this.citiesService.renameCity(cityId, body);

        return new ResponseNoIdDTO("Nome della città aggiornato correttamente", LocalDateTime.now());
    }
}
