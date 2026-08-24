package adastra.backend.DTO;

import adastra.backend.enums.SeatStatus;

import java.util.UUID;

public record BookedSeatsDTO(
        String color,
        String row,
        Integer number,
        String svgCoordinates,
        UUID id,
        SeatStatus seatStatus

) {
}
