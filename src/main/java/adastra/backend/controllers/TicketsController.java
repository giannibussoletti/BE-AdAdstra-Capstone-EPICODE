package adastra.backend.controllers;

import adastra.backend.entities.Movie;
import adastra.backend.entities.User;
import adastra.backend.services.TicketsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/tickets")
@AllArgsConstructor
public class TicketsController {

    private TicketsService ticketsService;

    @GetMapping("/user-movies")
    @ResponseStatus(HttpStatus.OK)
    public Set<Movie> findByUser(@AuthenticationPrincipal User authUser) {
        return this.ticketsService.findMovieByUser(authUser.getId());
    }

}
