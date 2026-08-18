package adastra.backend.controllers;

import adastra.backend.DTO.ResponseDTO;
import adastra.backend.DTO.TicketDTO;
import adastra.backend.entities.Ticket;
import adastra.backend.services.TicketsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/tickets")
public class TicketsController {

    private TicketsService ticketsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO save(@RequestBody TicketDTO body) {
        Ticket saved = this.ticketsService.save(body);
        return new ResponseDTO("Biglietto salvato correttamente", saved.getId(), LocalDateTime.now());
    }
}
