package com.example.delivery;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {

    public static void sendEmail(String recipient, String subject, String body) throws MessagingException {
        // Sender's email address and password
        final String username = "your_email@example.com";
        final String password = "your_email_password";

        // SMTP server properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.example.com"); // Replace with your SMTP server
        props.put("mail.smtp.port", "587"); // Replace with your SMTP server port

        // Create a session with authentication
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        // Create a MimeMessage object
        Message message = new MimeMessage(session);

        // Set From: header field
        message.setFrom(new InternetAddress(username));

        // Set To: header field
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));

        // Set Subject: header field
        message.setSubject(subject);

        // Set email body
        message.setText(body);

        // Send email
        Transport.send(message);
    }
}
