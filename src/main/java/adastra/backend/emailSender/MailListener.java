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

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void onBookingCreated(BookingEventCreated event) {

        emailService.emailSender(
                "Adastra Cinema <ticket@mail.adastracinema.it>",
                event.booking().getGuestMail(),
                "Ecco i tuoi biglietti",
                "<p>Hai comprato dei biglietti</p>",
                event.pdfTickets()

        );

    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void onUserCreated(UserAccountCreated userCreated) {

        emailService.emailSender(
                "Adastra Cinema <created@user.adastracinema.it>",
                userCreated.user().getEmail(),
                "Benvenuto su Adastra Cinema",
                "<p>Preparati a scoprire un mondo di film incredibili!</p>",
                null
        );

    }

}
