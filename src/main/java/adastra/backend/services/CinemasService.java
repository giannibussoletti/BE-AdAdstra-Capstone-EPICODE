package adastra.backend.services;

import adastra.backend.DTO.CinemaDTO;
import adastra.backend.entities.Cinema;
import adastra.backend.entities.City;
import adastra.backend.exceptions.NotFoundException;
import adastra.backend.repository.CinemasRepository;
import adastra.backend.softDelete.SoftDeleteMethod;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public void softDeleteGeneric(UUID cinemaId, String isDeleted) {
        super.softDeleteGeneric(cinemaId, isDeleted);
    }

    public Page<Cinema> findAll(int page, int size, String searchBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(searchBy));
        return this.cinemasRepository.findAll(pageable);
    }

    public Cinema save(CinemaDTO body) {
        City cinemaCity = this.citiesService.findCityById(body.cityId());
        return this.cinemasRepository.save(new Cinema(body.cinemaName().toLowerCase(), body.address().toLowerCase(), cinemaCity));
    }

    public void cinemaUpdate(CinemaDTO body, UUID cinemaId) {
        Cinema found = this.cinemasRepository.findById(cinemaId).orElseThrow(() -> new NotFoundException("Nessun cinema trovato con questo id"));
        found.setAddress(body.address().toLowerCase());
        found.setCinemaName(body.cinemaName().toLowerCase());
        this.cinemasRepository.save(found);
    }


    public Cinema findByID(UUID cinemaId) {
        return this.cinemasRepository.findById(cinemaId).orElseThrow(() -> new NotFoundException("Nessun cinema trovato con questo id"));
    }

    public List<Cinema> findByCityID(UUID cityId) {
        City found = this.citiesService.findCityById(cityId);
        return this.cinemasRepository.findCinemaByCityId(found).orElseThrow(() -> new NotFoundException("Nessun cinema trovato con questo id"));
    }
}
