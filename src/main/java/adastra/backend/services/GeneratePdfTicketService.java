package adastra.backend.services;

import adastra.backend.DTO.BookingDTO;
import adastra.backend.emailSender.TicketSeatAndQR;
import adastra.backend.entities.Booking;
import adastra.backend.entities.ScreeningTime;
import adastra.backend.entities.Seat;
import adastra.backend.entities.Ticket;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@AllArgsConstructor
public class GeneratePdfTicketService {

    @Autowired
    private TemplateEngine templateEngine;
    private ScreeningTimeService screeningTimeService;
    private TicketsService ticketsService;
    private SeatsService seatsService;

    protected String generateQrCodeBase64(String content, int size) throws WriterException, IOException {
        BitMatrix matrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, size, size);
        ByteArrayOutputStream qrBaos = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "PNG", qrBaos);
        return Base64.getEncoder().encodeToString(qrBaos.toByteArray());
    }

    public byte[] generatePdf(BookingDTO body, Booking booking) throws Exception {
        Context context = new Context();

        ScreeningTime found = this.screeningTimeService.findById(body.screenTimeId());
        LocalDateTime divideData = found.getDateTime();

        String screeningDate = String.format("%02d/%02d/%d",
                divideData.getDayOfMonth(), divideData.getMonthValue(), divideData.getYear());
        String time = String.format("%02d:%02d", divideData.getHour(), divideData.getMinute());

        List<Ticket> ticketsByBooking = this.ticketsService.findTicketsByBooking(booking);

        List<TicketSeatAndQR> tickets = new ArrayList<>();
        for (Ticket ticket : ticketsByBooking) {
            String qrCodeBase64 = generateQrCodeBase64(ticket.getId().toString(), 300);

            Seat foundSeat = this.seatsService.findById(ticket.getSeatId().getId());
            String seatPosition = String.format("%C%d", foundSeat.getRow(), foundSeat.getNumber());


            tickets.add(new TicketSeatAndQR(seatPosition, qrCodeBase64));
        }


        context.setVariable("movieTitle", found.getMovieId().getTitle());
        context.setVariable("screenNumber", found.getScreenId().getScreenNumber());
        context.setVariable("screeningDate", screeningDate);
        context.setVariable("time", time);
        context.setVariable("tickets", tickets);


        String html = templateEngine.process("ticket", context);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(baos);

        return baos.toByteArray();
    }
}

