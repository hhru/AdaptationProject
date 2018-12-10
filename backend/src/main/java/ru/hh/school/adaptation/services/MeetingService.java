package ru.hh.school.adaptation.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.enumeration.service.SendInvitationsMode;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.AttendeeCollection;
import microsoft.exchange.webservices.data.property.complex.FolderId;
import microsoft.exchange.webservices.data.property.complex.Mailbox;
import ru.hh.nab.core.util.FileSettings;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.misc.CommonUtils;

@Singleton
public class MeetingService {
  private static final Logger logger = LoggerFactory.getLogger(MeetingService.class);
  private final ExchangeService exchangeService;
  private final Integer meetingHour;
  private final Integer meetingDuration;

  @Inject
  public MeetingService(FileSettings fileSettings, ExchangeService exchangeService) throws URISyntaxException {
    String username = fileSettings.getString("mail.smtp.username");
    String password = fileSettings.getString("mail.smtp.password");
    String url = fileSettings.getString("mail.ews.url");

    this.exchangeService = exchangeService;
    this.exchangeService.setCredentials(new WebCredentials(username, password));
    this.exchangeService.setUrl(new URI(url));

    meetingHour = fileSettings.getInteger("meeting.hour");
    meetingDuration = fileSettings.getInteger("meeting.duration");
  }

  public void sendMeeting(String subject, String[] attendees, Employee employee, Date date) {
    String organizer = employee.getHr().getSelf().getEmail();
    String info = new StringBuilder(subject)
      .append(" (")
      .append(CommonUtils.makeFioFromPersonalInfo(employee.getSelf()))
      .append(")").toString();

    Calendar startDate = Calendar.getInstance();
    startDate.setTime(date);
    startDate.set(Calendar.HOUR_OF_DAY, meetingHour);
    startDate.set(Calendar.MINUTE, 0);
    startDate.set(Calendar.SECOND, 0);
    startDate.set(Calendar.MILLISECOND, 0);

    Calendar endDate = (Calendar) startDate.clone();
    endDate.add(Calendar.MINUTE, meetingDuration);

    Runnable task = () -> {
      try {
        Appointment appointment = new Appointment(exchangeService);
        AttendeeCollection requiredAttendees = appointment.getRequiredAttendees();
        FolderId folderId = new FolderId(WellKnownFolderName.Calendar, Mailbox.getMailboxFromString(organizer));

        appointment.setSubject(info);
        appointment.setStart(startDate.getTime());
        appointment.setEnd(endDate.getTime());

        for (String attendee : attendees) {
          requiredAttendees.add(attendee);
        }

        appointment.save(folderId, SendInvitationsMode.SendOnlyToAll);
      } catch (Exception e) {
        logger.error("Unable to send meeting");
        logger.error(e.getStackTrace().toString());
      }
    };

    logger.debug("Trying to create and send meeting for %s", organizer);
    task.run();
  }
}
