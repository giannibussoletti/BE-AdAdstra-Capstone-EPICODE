package adastra.backend.services;

import adastra.backend.DTO.RowUpdateDTO;
import adastra.backend.DTO.SeatDTO;
import adastra.backend.DTO.UpdateSingleSeatDTO;
import adastra.backend.entities.Screen;
import adastra.backend.entities.Seat;
import adastra.backend.exceptions.NotFoundException;
import adastra.backend.repository.SeatsRepository;
import adastra.backend.specifications.SeatSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SeatsService {

    private SeatsRepository seatsRepository;
    private ScreensService screensService;


    public ArrayList<UUID> save(SeatDTO body) {
        List<String> seatColors = List.of("green", "blue", "red");
        boolean trueColor = seatColors.stream().noneMatch(color -> Objects.equals(body.color(), color));
        if (trueColor) throw new NotFoundException("Il colore può essere solo, green, blue o red");
        List<Screen> foundScreens = this.screensService.findAllById(body.screenIds());

        ArrayList<UUID> seatsIds = new ArrayList<>();
        for (int i = (body.minNumber() - 1); i < body.maxNumber(); i++) {
            seatsIds.add(this.seatsRepository.save(new Seat(body.color(), body.row(), (i + 1), body.svgCoordinates().get(i), foundScreens)).getId());
        }
        return seatsIds;
    }

    public void updateRow(RowUpdateDTO body) {
        List<String> seatColors = List.of("green", "blue", "red");
        boolean trueColor = seatColors.stream().noneMatch(color -> Objects.equals(body.color(), color));
        if (trueColor) throw new NotFoundException("Il colore può essere solo, green, blue o red");
        Screen found = this.screensService.findById(body.screenId());
        body.seats().forEach(uuid -> {
            Seat seatFound = this.findById(uuid);
            seatFound.setColor(body.color());
            seatFound.setRow(body.row());
            this.seatsRepository.save(seatFound);

        });

    }

    public void updateSingleSeat(UpdateSingleSeatDTO body, UUID seatId) {
        List<String> seatColors = List.of("green", "blue", "red");
        boolean trueColor = seatColors.stream().noneMatch(color -> Objects.equals(body.color(), color));
        if (trueColor) throw new NotFoundException("Il colore può essere solo, green, blue o red");
        Seat found = this.findById(seatId);
        found.setRow(body.row());
        found.setColor(body.color());
        found.setNumber(body.number());
        this.seatsRepository.save(found);

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

    public Seat findById(UUID seatId) {
        return this.seatsRepository.findById(seatId).orElseThrow(() -> new NotFoundException("Nessun posto con questo id trovato"));
    }

}
