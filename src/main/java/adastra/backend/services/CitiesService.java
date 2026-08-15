package adastra.backend.services;

import adastra.backend.DTO.CityDTO;
import adastra.backend.entities.City;
import adastra.backend.repository.CitiesRepository;
import adastra.backend.softDeletion.SoftDeleteMethod;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CitiesService extends SoftDeleteMethod<City, UUID> {

    private CitiesRepository citiesRepository;

    @Override
    protected JpaRepository<City, UUID> getRepository() {
        return this.citiesRepository;
    }

    @Override
    protected String getEntityName() {
        return "città";
    }

    public City save(CityDTO body) {
        City saved = new City(body.name());
        return this.citiesRepository.save(saved);
    }

    @Override
    public void softDeleteGeneric(UUID entityId, String body) {
        super.softDeleteGeneric(entityId, body);
    }
}
