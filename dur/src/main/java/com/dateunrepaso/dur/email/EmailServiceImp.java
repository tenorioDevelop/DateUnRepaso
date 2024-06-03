package com.dateunrepaso.dur.email;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImp implements IEmailService {

    private final JavaMailSender javaMailSender;

    public EmailServiceImp(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void enviarCorreo(EmailDTO correoRequest) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
    
            helper.setTo(correoRequest.getDestinatario());
            helper.setSubject(correoRequest.getAsunto());
    
            // Agregar el contenido del mensaje
            helper.setText(correoRequest.getMensaje(), true); // El segundo parámetro indica que el contenido es HTML
    
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar el correo: " + e.getMessage(), e);
        }
    }
    
}
