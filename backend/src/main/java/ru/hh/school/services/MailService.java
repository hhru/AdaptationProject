package ru.hh.school.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailService {
    private String login;
    private String password;
    private Properties properties;
    private Session session;
    private ExecutorService threadService = Executors.newFixedThreadPool(1);
    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    public MailService(String serviceLogin, String servicePassword){
        login = serviceLogin;
        password = servicePassword;

        properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(login, password);
            }
        });
    }

    public Future<Integer> sendMail(String userName, String userEmail, boolean isMale) {
        Callable<Integer> task = () -> {
            String messageHtml=getTemplateString(userName, isMale);
            if (messageHtml == null) {return 1;}
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(login));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));
                message.setSubject("Testing HtmlParse");
                message.setContent(messageHtml, "text/html; charset=utf-8");

                Transport.send(message);
                logger.info("Email for {} has been send", userEmail);
                return 0;
            } catch (MessagingException e) {
                logger.warn("Something went wrong with sending email for {} ", userEmail);
                return 1;
            }
        };
        logger.info("Try send email for {} ", userEmail);
        Future<Integer> future = threadService.submit(task);
        return future;
    }

    public String getTemplateString(String userName, boolean isMale) {
        Path filePath = FileSystems.getDefault().getPath("templates", "welcome.html");
        byte[] byteArray=null;
        try {
            byteArray = Files.readAllBytes(filePath);
        } catch (IOException e) {
            logger.warn("Can't read file {} ", filePath);
            return null;
        }
        String templateString=null;
        try {
            templateString = new String(byteArray,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.warn("Unsupported encoding in {} ", filePath);
            return null;
        }
        if (!templateString.contains("{{userName}}") || !templateString.contains("{{isMale}}")) {
            logger.warn("Invalid templates parameters");
            return null;
        }
        templateString = templateString.replace("{{userName}}",userName);
        templateString = templateString.replace("{{isMale}}",isMale?"провёл":"провела");
        return templateString;
    }

}
