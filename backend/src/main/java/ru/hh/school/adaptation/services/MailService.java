package ru.hh.school.adaptation.services;

import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.misc.CommonUtils;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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
import javax.mail.internet.MimeUtility;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.mail.PasswordAuthentication;

import biweekly.Biweekly;
import biweekly.ICalVersion;
import biweekly.ICalendar;
import biweekly.component.VAlarm;
import biweekly.component.VEvent;
import biweekly.property.Classification;
import biweekly.property.Method;
import biweekly.property.Trigger;
import biweekly.util.Duration;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.nab.core.util.FileSettings;

@Singleton
public class MailService {
  private final String templatePath;
  private final Session session;
  private static final Logger logger = LoggerFactory.getLogger(MailService.class);

  @Inject
  public MailService(FileSettings fileSettings){
    final String username = fileSettings.getProperties().getProperty("mail.smtp.username");
    final String password = fileSettings.getProperties().getProperty("mail.smtp.password");

    this.session = Session.getInstance(fileSettings.getProperties(),
      new javax.mail.Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(username, password);
        }
      });

    templatePath = System.getProperty("templatesDir") + "/mail/";
  }

  public void sendMail(String email, String template, String subject, Map<String, String> params){
    String message;
    try {
      message = new String(Files.readAllBytes(Paths.get(templatePath + template)));
    } catch (IOException e) {
      logger.error("Can't read mail template file: {}\n{}", template, e);
      return;
    }

    String text = applyTemplate(message, params);
    sendMail(email, text, subject);
  }

  public void sendMail(String toEmail, String messageHtml, String subject) {
    if (messageHtml == null) {
      throw new IllegalArgumentException("message is null");
    }

    Runnable task = () -> {
      try {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(session.getProperties().getProperty("mail.smtp.from.fullName")));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject(MimeUtility.encodeText(subject, "utf-8", "B"));
        message.setContent(messageHtml, "text/html; charset=utf-8");

        Transport.send(message);
        logger.info("Email for {} has been send", toEmail);
      } catch (MessagingException | UnsupportedEncodingException e) {
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

  public ICalendar makeICal(String organizer, String[] attendees, String summary, LocalDateTime date, Integer duration) {
    Date dateStart = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());

    ICalendar icalendar = new ICalendar();
    icalendar.setMethod(Method.REQUEST);
    icalendar.setVersion(ICalVersion.V2_0);
    VEvent event = new VEvent();
    event.setSummary(summary);
    event.setOrganizer(organizer);
    for (String attendee : attendees) {
      event.addAttendee(attendee);
    }
    event.setPriority(3);
    event.setClassification(Classification.PUBLIC);
    event.setDateStart(dateStart);
    event.setDuration(new Duration.Builder().minutes(duration).build());
    event.addAlarm(VAlarm.display(new Trigger(DateUtils.addMinutes(dateStart, -30)), summary));
    icalendar.addEvent(event);

    return icalendar;
  }

  public void sendMeeting(String subject, String recipient, String[] attendees, Employee employee, LocalDateTime date, Integer duration) {
    String[] recipients = new String[]{recipient};
    sendMeeting(subject, recipients, attendees, employee, date, duration);
  }

  public void sendMeeting(String subject, String[] recipients, String[] attendees, Employee employee, LocalDateTime date, Integer duration) {
    String organizer = employee.getHr().getSelf().getEmail();
    String info = new StringBuilder(subject)
      .append(" (")
      .append(CommonUtils.makeFioFromPersonalInfo(employee.getSelf()))
      .append(")")
      .toString();
    ICalendar icalendar = makeICal(organizer, attendees, info, date, duration);

    for (String recipient : recipients) {
      sendCalendar(icalendar, recipient, info);
    }
  }

  public void sendCalendar(ICalendar icalendar, String recipient, String subject) {
    Runnable task = () -> {
      try {
        MimeBodyPart messageCalendar = new MimeBodyPart();
        messageCalendar.setHeader("Content-Class", "urn:content-classes:calendarmessage");
        messageCalendar.setHeader("Content-ID", "calendar_message");
        messageCalendar.setDataHandler(new DataHandler(new ByteArrayDataSource(Biweekly.write(icalendar).go(), "text/calendar;charset=utf-8")));

        Multipart multipart = new MimeMultipart("alternative");
        multipart.addBodyPart(messageCalendar);

        MimeMessage message = new MimeMessage(session);
        message.addHeaderLine("method=REQUEST");
        message.addHeaderLine("charset=UTF-8");
        message.addHeaderLine("component=VEVENT");
        message.setFrom(new InternetAddress(session.getProperties().getProperty("mail.smtp.from.fullName")));
        message.addRecipients(Message.RecipientType.TO, recipient);
        message.setSubject(MimeUtility.encodeText(subject, "utf-8", "B"));
        message.setContent(multipart);

        Transport.send(message);
        logger.info("Calendar for {} has been send", recipient);
      } catch (MessagingException|IOException e) {
        logger.warn("Something went wrong with sending calendar for {} ", recipient);
      }
    };
    logger.info("Try send calendar for {} ", recipient);
    task.run();
  }
}
