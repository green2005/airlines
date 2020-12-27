package by.epamtraining.airlines.service.impl;

import by.epamtraining.airlines.domain.User;
import by.epamtraining.airlines.service.EmailProperties;
import by.epamtraining.airlines.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.zip.CRC32;
import java.util.zip.Checksum;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    EmailProperties emailProperties;

    @Override
    public void sendRegistrationEmail(User user) {

        String to = user.getEmail();
        String from = emailProperties.getDisplayfrom();

        // Get system properties
        final String username = emailProperties.getUsername();
        final String password = emailProperties.getPassword();

        Properties prop = new Properties();
        prop.put("mail.smtp.host", emailProperties.getHost());
        prop.put("mail.smtp.port", emailProperties.getPort());
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject("Activation link for somewhereairlines.com");

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(getMessage(user), "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
        } catch (MessagingException mex) {
            throw new RuntimeException(mex);
        }

    }

    private String getMessage(User user) {
        return new StringBuilder("Hello ").append(user.getName()).append("! ")
                .append("To activate your account press next link: ").append(emailProperties.getDomainHost())
                .append("/activate?userid=")
                .append(user.getId())
                .append("&code=" + user.getCRC())
                .toString();
    }
}
