package com.watcher.cripto.trader.service;

import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Endpoint(id = "mailer")
@Service
public class MailService {

    @ReadOperation
    public void send() throws Exception {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("pepe.aduna@gmail.com", "ncvl qreq blat jmyj");
            }
        });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("correo-diferente@gmail.com"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("pepe.aduna@gmail.com"));
        message.setSubject("Solicitud");
        message.setText("buenas tardes");
        Transport.send(message);
        System.out.println("Correo enviado con éxito.");

    }
}
