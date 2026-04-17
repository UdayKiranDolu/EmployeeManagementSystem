package com.employeemanagementsystem.ems.services;

import com.employeemanagementsystem.ems.entity.Email;
import com.employeemanagementsystem.ems.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private JavaMailSender emailSender;

    public void sendEmail(String to, String subject, String body){

        Email e = new Email();
        e.setRecipient(to);
        e.setSubject(subject);
        e.setBody(body);
        e.setSentAt(LocalDate.from(LocalDateTime.now()));

        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(subject);
            message.setTo(to);
            message.setText(body);

            emailSender.send(message);
            e.setStatus("Success");
        }
        catch (Exception ex){
            e.setStatus("Failure");
            System.out.print(ex.getMessage());
        }
        emailRepository.save(e);
    }


    private void sendResetLink(String toEmail, String ResetLink){

        String subject = "Password Reset Request Link";

        String body= "Hello \n\n Click the below link to reset your password :\n" + ResetLink + "\n\n If you did not request this link , please ignore the link ";

        sendEmail(toEmail,subject, body);

    }
}
