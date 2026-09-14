package adastra.backend.emailSender;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailListener {

    private final EmailService emailService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onBookingCreated(BookingEventCreated event) {
        try {
            emailService.emailSender(
                    "Adastra Cinema <ticket@mail.adastracinema.it>",
                    event.booking().getGuestMail(),
                    "Ecco i tuoi biglietti",
                    "<p>Hai comprato dei biglietti</p>"
            );
        } catch (Exception e) {
            log.warn("Booking {} confermato ma invio email fallito", event.booking().getId(), e);
        }
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onUserCreated(UserAccountCreated userCreated) {
        try {
            emailService.emailSender(
                    "Adastra Cinema <created@user.adastracinema.it>",
                    userCreated.user().getEmail(),
                    "Benvenuto su Adastra Cinema",
                    "<p>Preparati a scoprire un mondo di film incredibili!</p>"
            );
        } catch (Exception e) {
            log.warn("Booking {} confermato ma invio email fallito", userCreated.user().getId(), e);
        }
    }

}
