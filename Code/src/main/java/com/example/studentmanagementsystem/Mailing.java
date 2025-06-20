package com.example.studentmanagementsystem;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Mailing {

    /**
     *
     * @param toEmail
     * @param subject
     * @param body
     */

    // Session holds configuration settings for sending emails, such as email server details
    // (e.g., SMTP host, port, authentication), and it manages connections to the mail server.


    public static void sendEmail(String toEmail, String subject, String body )  {

        Properties prop = EmailConfig.getEmailProperties();
        String username = EmailConfig.getMail();
        String pass = EmailConfig.getPass();

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, pass);
            }
        });
        try {
            //(MIME: Multipurpose Internet Mail Extensions) enables you to create both simple and complex
            // email messages by setting properties like recipients, sender, subject, body, and attachments.
            MimeMessage message = new MimeMessage(session);
            // set the sender's email
            message.setFrom(new InternetAddress(EmailConfig.getMail()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(subject);
            message.setText(body);

            // send the email
            Transport.send(message);
            System.out.println("The email was sent successfully !");

        }catch(MessagingException e){
            e.printStackTrace();
            System.out.println("Failed to send email:" + e.getMessage());
        }
    }
}
