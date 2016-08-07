package com.apibatmap.restjersey.dao;

/**
 * Created by lahiru on 8/6/2016.
 */

import org.json.JSONObject;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailDao {

    private static final String gmailUsername = "batmap.uoc.official@gmail.com" ;
    private static final String gmailPassword = "batmapuoc@123";

    static Properties mailServerProperties;
    static Session getMailSession;
    static MimeMessage generateMailMessage;

    private String recipient;
    private String subject;
    private String body;



    public EmailDao(JSONObject jsonReq){
        this.setRecipient(jsonReq.getString("recipient"));
        this.setSubject(jsonReq.getString("subject"));
        this.setBody(jsonReq.getString("body"));
    }

    public boolean sendMail() throws AddressException, MessagingException {
        Boolean flag = generateEmail();
        System.out.println("\n\n ===> Your Java Program has just sent an Email successfully. Check your email..");
        return flag;
    }

    public boolean generateEmail() throws AddressException, MessagingException {
        Boolean flag = true;

        // setup Mail Server Properties
        System.out.println("\n 1st ===> setup Mail Server Properties..");
        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");
        mailServerProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        System.out.println("Mail Server Properties have been setup successfully..");

        // get Mail Session.
        System.out.println("\n\n 2nd ===> get Mail Session..");
        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(this.recipient));
//        generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("test2@crunchify.com"));
        generateMailMessage.setSubject(this.subject);
        String emailBody = this.body;
        generateMailMessage.setContent(emailBody, "text/html");
        System.out.println("Mail Session has been created successfully..");

        // Get Session and Send mail
        System.out.println("\n\n 3rd ===> Get Session and Send mail");

        try {
            Transport transport = getMailSession.getTransport("smtp");
            transport.connect("smtp.gmail.com", gmailUsername, gmailPassword);
            transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
            transport.close();
        }catch (Exception e){
            flag = false;
        }


        return flag;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
