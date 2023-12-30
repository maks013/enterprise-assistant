package com.enterpriseassistant.infrastructure.email;

import com.enterpriseassistant.infrastructure.email.configuration.properties.EmailConfigurationProperties;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
class EmailService implements EmailSender {

    private final EmailConfigurationProperties emailConfigurationProperties;
    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    public void sendWithAttachment(String to, String clientName, byte[] attachment, String invoiceNumber) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            String emailContent = EmailBuilder.buildInvoiceEmail(clientName, invoiceNumber);

            helper.setTo(to);
            helper.setSubject(emailConfigurationProperties.subject() + " " + invoiceNumber);
            helper.setFrom(emailConfigurationProperties.from());
            helper.setText(emailContent, true);
            helper.addAttachment("Faktura-" + invoiceNumber + ".pdf", new ByteArrayResource(attachment));
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }
    }

}
