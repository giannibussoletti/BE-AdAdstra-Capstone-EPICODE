package adastra.backend.emailSender;

import adastra.backend.DTO.BookingDTO;
import adastra.backend.entities.Booking;

public record BookingEventCreated(Booking booking, byte[] pdfTickets, BookingDTO bookingDTO) {
}
