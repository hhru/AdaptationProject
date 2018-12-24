package ru.hh.school.adaptation.services.workflow;

import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;

import ru.hh.school.adaptation.entities.PersonalInfo;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.entities.ScheduledMail;
import static ru.hh.school.adaptation.entities.ScheduledMailType.CHIEF_TASK;
import static ru.hh.school.adaptation.entities.ScheduledMailType.PROBATION_RESULT;
import static ru.hh.school.adaptation.entities.ScheduledMailType.WELCOME;
import ru.hh.school.adaptation.services.MeetingService;
import ru.hh.school.adaptation.services.ScheduledMailService;

import javax.inject.Singleton;
import java.util.Optional;
import java.util.stream.Stream;

@Singleton
public class AddStep {

  private MeetingService meetingService;
  private ScheduledMailService scheduledMailService;

  public AddStep(MeetingService meetingService, ScheduledMailService scheduledMailService) {
    this.meetingService = meetingService;
    this.scheduledMailService = scheduledMailService;
  }

  public void onAdd(Employee employee) {
    String[] attendees = Stream.of(
        employee.getSelf().getEmail(),
        employee.getHr().getSelf().getEmail(),
        employee.getChief().getEmail(),
        Optional.ofNullable(employee.getMentor()).orElse(new PersonalInfo()).getEmail()
    ).filter((str) -> str != null).toArray(String[]::new);

    sendMeetings(attendees, employee);

    taskListMail(employee);
    welcomeMail(employee);
    probationResultMail(employee);
  }

  private void sendMeetings(String[] attendees, Employee employee) {
    meetingService.sendMeeting("Welcome-встреча", attendees, employee, employee.getEmploymentDate());
    meetingService.sendMeeting("Промежуточная встреча", attendees, employee, employee.getInterimDate());
    meetingService.sendMeeting("Итоговая встреча", attendees, employee, employee.getFinalDate());
  }

  private void welcomeMail(Employee employee) {
    ScheduledMail scheduledMail = new ScheduledMail();
    scheduledMail.setEmployeeId(employee.getId());
    scheduledMail.setType(WELCOME);
    scheduledMail.setRecipients(employee.getSelf().getEmail());
    scheduledMail.setTriggerDate(DateUtils.addHours(employee.getEmploymentDate(), 8));
    scheduledMailService.scheduleNewMail(scheduledMail);
  }

  private void taskListMail(Employee employee) {
    ScheduledMail scheduledMail = new ScheduledMail();
    scheduledMail.setEmployeeId(employee.getId());
    scheduledMail.setType(CHIEF_TASK);
    scheduledMail.setRecipients(employee.getChief().getEmail());
    scheduledMail.setTriggerDate(new Date());
    scheduledMailService.scheduleNewMail(scheduledMail);
  }

  private void probationResultMail(Employee employee) {
    ScheduledMail scheduledMail = new ScheduledMail();
    scheduledMail.setEmployeeId(employee.getId());
    scheduledMail.setType(PROBATION_RESULT);
    scheduledMail.setRecipients(employee.getChief().getEmail());
    scheduledMail.setTriggerDate(DateUtils.addHours(DateUtils.addDays(employee.getFinalDate(), -3), 8));
    scheduledMailService.scheduleNewMail(scheduledMail);
  }

}
