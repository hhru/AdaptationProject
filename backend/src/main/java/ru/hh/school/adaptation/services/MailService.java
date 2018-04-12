package ru.hh.school.adaptation.services;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.nab.core.util.FileSettings;
import ru.hh.school.adaptation.dao.MailTemplateDao;
import ru.hh.school.adaptation.entities.MailTemplate;

@Singleton
public class MailService {
  private final Session session;
  private MailTemplateDao mailTemplateDao;
  private static final Logger logger = LoggerFactory.getLogger(MailService.class);

  @Inject
  public MailService(FileSettings fileSettings, MailTemplateDao mailTemplateDao){
    this.session = Session.getInstance(fileSettings.getProperties());
    this.mailTemplateDao = mailTemplateDao;
  }

  public void sendMail(String userEmail, String messageName, Map<String, String> parameterMap){
    MailTemplate mailTemplate = mailTemplateDao.getRecordByName(messageName);
    String text = applyTemplate(mailTemplate.getHtml(), parameterMap);
    String title = applyTemplate(mailTemplate.getTitle(), parameterMap);
    sendMail(userEmail, text, title);
  }

  private void sendMail(String userEmail, String messageHtml, String subject) {
    if (messageHtml == null) {
      throw new IllegalArgumentException("message is null");
    }

    Runnable task = () -> {
      try {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(session.getProperties().getProperty("mail.smtp.from.name")));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));
        message.setSubject(subject);
        message.setContent(messageHtml, "text/html; charset=utf-8");

        Transport.send(message);
        logger.info("Email for {} has been send", userEmail);
      } catch (MessagingException e) {
        logger.warn("Something went wrong with sending email for {} ", userEmail);
      }
    };
    logger.info("Try send email for {} ", userEmail);
    task.run();
  }

  public String applyTemplate(String template, Map<String, String> params) {
    for(Map.Entry<String, String> item: params.entrySet()) {
      template = template.replace(item.getKey(), item.getValue());
    }
    return template;
  }
}
