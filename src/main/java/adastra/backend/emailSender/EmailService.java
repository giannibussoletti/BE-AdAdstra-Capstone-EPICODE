package adastra.backend.emailSender;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.Attachment;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final Resend resendClient;

    public void emailSenderTicket(String from, String to, String subject, String htmlBody, byte[] pdfTickets) {

        String base64pdf = Base64.getEncoder().encodeToString(pdfTickets);

        Attachment attachment = Attachment.builder()
                .fileName("biglietti.pdf")
                .content(base64pdf)
                .build();


        CreateEmailOptions params = CreateEmailOptions.builder()
                .from(from)
                .to(to)
                .subject(subject)
                .html(htmlBody)
                .attachments(List.of(attachment))
                .build();

        try {
            CreateEmailResponse response = resendClient.emails().send(params);
            log.info("Email inviata con id: {}", response.getId());
        } catch (ResendException e) {
            log.error("Invio email fallito per {}: {}", to, e.getMessage(), e);
            throw new RuntimeException("Invio email fallito", e);
        }
    }

    public void emailSenderUser(String from, String to, String subject, String htmlBody) {
        
        CreateEmailOptions params = CreateEmailOptions.builder()
                .from(from)
                .to(to)
                .subject(subject)
                .html(htmlBody)
                .build();

        try {
            CreateEmailResponse response = resendClient.emails().send(params);
            log.info("Email inviata con id: {}", response.getId());
        } catch (ResendException e) {
            log.error("Invio email fallito per {}: {}", to, e.getMessage(), e);
            throw new RuntimeException("Invio email fallito", e);
        }
    }

}
