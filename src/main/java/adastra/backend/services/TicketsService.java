package adastra.backend.services;

import adastra.backend.DTO.UserMoviesDTO;
import adastra.backend.entities.*;
import adastra.backend.repository.TicketsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TicketsService {

    private TicketsRepository ticketsRepository;
    private ScreeningTimeService screeningTimeService;
    private UsersService usersService;


    public Ticket save(Booking booking, ScreeningTime time, Seat seat) {
        return this.ticketsRepository.save(new Ticket(booking, seat, time));

    }

    public List<Seat> findSeatByScreeningTime(UUID timeId) {
        ScreeningTime found = this.screeningTimeService.findById(timeId);
        List<Ticket> ticketsFound = this.ticketsRepository.findTicketByScreeningTimeId(found);
        return ticketsFound.stream().map(Ticket::getSeatId).toList();
    }

    public Set<UserMoviesDTO> findMovieByUser(UUID userId) {
        User found = this.usersService.findById(userId);
        List<Ticket> ticketList = this.ticketsRepository.findMovieByUser(found);

        return ticketList.stream()
                .collect(Collectors.toMap(
                        ticket -> ticket.getScreeningTimeId().getMovieId().getId(),
                        ticket -> {

                            Seat seat = ticket.getSeatId();
                            String seatPos = seat.getRow() + Integer.toString(seat.getNumber());
                            ScreeningTime screeningTime = ticket.getScreeningTimeId();
                            Movie movie = screeningTime.getMovieId();
                            return new UserMoviesDTO(movie.getTitle(), movie.getId(), seatPos, screeningTime.getDateTime(), screeningTime.getScreenId().getScreenNumber()
                            );
                        },
                        (first, last) -> first
                ))
                .values()
                .stream()
                .collect(Collectors.toSet());

    }
}