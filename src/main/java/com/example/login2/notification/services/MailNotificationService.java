package com.example.login2.notification.services;

import com.example.login2.users.entities.User;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MailNotificationService {
    public void sendActivationMail(User user) throws IOException
    {
        SendGrid sendGrid = new SendGrid("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");

        Email from = new Email("tironegiovanni4@gmail.com");
        Email to = new Email(user.getEmail());
        String subject = "Ti sei iscritto alla piattaforma";
        String text = "Il codice di attivazione è: " + user.getActivationCode();
        Content content = new Content("text/plain", text);
        Mail mail = new Mail(from, subject, to, content);

        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        Response response = sendGrid.api(request);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
        System.out.println(response.getHeaders());
    }

    public void sendPwsResetMail(User user) throws IOException
    {
        SendGrid sendGrid = new SendGrid("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");

        Email from = new Email("tironegiovanni4@gmail.com");
        Email to = new Email(user.getEmail());
        String subject = "Ti sei iscritto alla piattaforma";
        String text = "Il codice di attivazione è: " + user.getPwdResetCode();
        Content content = new Content("text/plain", text);
        Mail mail = new Mail(from, subject, to, content);

        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        Response response = sendGrid.api(request);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
        System.out.println(response.getHeaders());
    }
}
