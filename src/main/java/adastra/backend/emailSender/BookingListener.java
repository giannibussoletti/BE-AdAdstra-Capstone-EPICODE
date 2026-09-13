package adastra.backend.emailSender;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookingListener {

    private final EmailService emailService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onBookingCreated(BookingEventCreated event) {
        try {
            emailService.emailSender(
                    event.booking().getGuestMail(),
                    "Ecco i tuoi biglietti",
                    "<p>Hai comprato dei biglietti</p>"
            );
        } catch (Exception e) {
            log.warn("Booking {} confermato ma invio email fallito", event.booking().getId(), e);
        }
    }
}
