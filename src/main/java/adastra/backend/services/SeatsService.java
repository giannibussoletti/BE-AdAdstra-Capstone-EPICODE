package adastra.backend.services;

import adastra.backend.DTO.SeatDTO;
import adastra.backend.entities.Screen;
import adastra.backend.entities.Seat;
import adastra.backend.exceptions.NotFoundException;
import adastra.backend.repository.SeatsRepository;
import adastra.backend.softDelete.SoftDeleteMethod;
import adastra.backend.specifications.SeatSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SeatsService extends SoftDeleteMethod<Seat, UUID> {

    private SeatsRepository seatsRepository;
    private ScreensService screensService;

    @Override
    protected JpaRepository<Seat, UUID> getRepository() {
        return this.seatsRepository;
    }

    @Override
    protected String getEntityName() {
        return "posto";
    }

    @Override
    public void softDeleteGeneric(UUID entityId, String body) {
        super.softDeleteGeneric(entityId, body);
    }

    public ArrayList<UUID> save(SeatDTO body) {
        List<String> seatColors = List.of("green", "blue", "red");
        boolean trueColor = seatColors.stream().noneMatch(color -> Objects.equals(body.color(), color));
        if (trueColor) throw new NotFoundException("Il colore può essere solo, green, blue o red");
        int seatNumber = body.number();
        Screen found = this.screensService.findById(body.screenId());
        ArrayList<UUID> seatsIds = new ArrayList<>();
        while (seatNumber > 0) {
            Seat saved = this.seatsRepository.save(new Seat(body.row(), seatNumber, found, body.color()));
            seatNumber -= 1;
            seatsIds.add(saved.getId());
        }
        return seatsIds;

    }

    public Page<Seat> findAll(int page, int size, String searchBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(searchBy));
        return this.seatsRepository.findAll(pageable);
    }

    public List<Seat> findAll(UUID cinemaId, Character row, Integer number, UUID screenId) {

        Specification<Seat> spec = Specification.where(SeatSpecification.filterByCinema(cinemaId));

        if (number != null) {
            spec = spec.and(SeatSpecification.filterByNumber(number));
        }

        if (row != null) {
            spec = spec.and(SeatSpecification.filterByRow(row));
        }

        if (screenId != null) {
            spec = spec.and(SeatSpecification.filterByScreen(screenId));
        }
        
        return this.seatsRepository.findAll(spec);
    }


}
