package ru.hh.school.adaptation.services;

import java.io.IOException;
import java.util.Map;

import javax.activation.DataHandler;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.nab.core.util.FileSettings;

@Singleton
public class MailService {
  private final Session session;
  private static final Logger logger = LoggerFactory.getLogger(MailService.class);

  @Inject
  public MailService(FileSettings fileSettings){
    session = Session.getInstance(fileSettings.getProperties());
  }

  public void sendMail(String toEmail, String messageHtml, String subject) {
    if (messageHtml == null) {
      throw new IllegalArgumentException("message is null");
    }

    Runnable task = () -> {
      try {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(session.getProperties().getProperty("mail.smtp.from.name")));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject(subject);
        message.setContent(messageHtml, "text/html; charset=utf-8");

        Transport.send(message);
        logger.info("Email for {} has been send", toEmail);
      } catch (MessagingException e) {
        logger.warn("Something went wrong with sending email for {} ", toEmail);
      }
    };
    logger.info("Try send email for {} ", toEmail);
    task.run();
  }

  public String applyTemplate(String template, Map<String, String> params) {
    for(Map.Entry<String, String> item: params.entrySet()) {
      template = template.replace(item.getKey(), item.getValue());
    }
    return template;
  }

  public void sendCalendar(String toEmail, String summary, String date) {
    Runnable task = () -> {
      try {
        MimeMessage message = new MimeMessage(session);
        message.addHeaderLine("method=REQUEST");
        message.addHeaderLine("charset=UTF-8");
        message.addHeaderLine("component=VEVENT");

        message.setFrom(new InternetAddress(session.getProperties().getProperty("mail.smtp.from.name")));
        message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject("Outlook Meeting Request Using JavaMail");

        StringBuffer sb = new StringBuffer();
        StringBuffer buffer = sb.append("BEGIN:VCALENDAR\n" +
                "VERSION:2.0\n" +
                "METHOD:REQUEST\n" +
                "BEGIN:VEVENT\n" +
                "DTSTART:" + date + "T100000\n" +
                "DTEND:" + date + "T110000\n" +
                "SUMMARY:" + summary + "\n" +
                "ORGANIZER:MAILTO:" + toEmail + "\n" +
                "CLASS:PUBLIC\n" +
                "PRIORITY:3\n" +
                "BEGIN:VALARM\n" +
                "TRIGGER:-PT30M\n" +
                "ACTION:DISPLAY\n" +
                "END:VALARM\n" +
                "END:VEVENT\n" +
                "END:VCALENDAR");

        MimeBodyPart messageCalendar = new MimeBodyPart();
        messageCalendar.setHeader("Content-Class", "urn:content-classes:calendarmessage");
        messageCalendar.setHeader("Content-ID", "calendar_message");
        messageCalendar.setDataHandler(new DataHandler(new ByteArrayDataSource(buffer.toString(), "text/calendar")));

        Multipart multipart = new MimeMultipart("alternative");
        multipart.addBodyPart(messageCalendar);

        message.setContent(multipart);

        Transport.send(message);
        logger.info("Calendar for {} has been send", toEmail);
      } catch (MessagingException|IOException e) {
        logger.warn("Something went wrong with sending calendar for {} ", toEmail);
      }
    };
    logger.info("Try send calendar for {} ", toEmail);
    task.run();
  }
}
