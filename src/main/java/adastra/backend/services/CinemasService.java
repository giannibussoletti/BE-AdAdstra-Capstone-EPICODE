package adastra.backend.services;

import adastra.backend.DTO.CinemaDTO;
import adastra.backend.entities.Cinema;
import adastra.backend.entities.City;
import adastra.backend.repository.CinemasRepository;
import adastra.backend.softDeletion.SoftDeleteMethod;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CinemasService extends SoftDeleteMethod<Cinema, UUID> {

    private CinemasRepository cinemasRepository;
    private CitiesService citiesService;

    @Override
    protected JpaRepository<Cinema, UUID> getRepository() {
        return this.cinemasRepository;
    }

    @Override
    protected String getEntityName() {
        return "cinema";
    }

    public Cinema save(CinemaDTO body) {
        City cinemaCity = this.citiesService.findCityById(body.cityId());
        return new Cinema(body.cinemaName(), body.address(), cinemaCity);
    }


}
