package ru.hh.school.adaptation.services.workflow;

import org.apache.commons.lang3.time.DateUtils;
import ru.hh.nab.core.util.FileSettings;

import ru.hh.school.adaptation.entities.PersonalInfo;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.entities.TaskForm;
import ru.hh.school.adaptation.services.MailService;
import ru.hh.school.adaptation.services.MeetingService;
import ru.hh.school.adaptation.services.ScheduledMailService;
import ru.hh.school.adaptation.services.TaskService;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Singleton
public class AddStep {

  private final String addTaskLink;

  private MailService mailService;
  private MeetingService meetingService;
  private TaskService taskService;
  private ScheduledMailService scheduledMailService;

  public AddStep(FileSettings fileSettings, MailService mailService, MeetingService meetingService, TaskService taskService,
                 ScheduledMailService scheduledMailService) {
    this.mailService = mailService;
    this.meetingService = meetingService;
    this.taskService = taskService;
    this.scheduledMailService = scheduledMailService;

    addTaskLink = "https://" + fileSettings.getProperties().getProperty("adaptation.host") + "/add_tasks/%s";
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
    scheduledMailService.scheduleNewMail(employee.getId(), DateUtils.addHours(employee.getEmploymentDate(), 8));
  }

  private void sendMeetings(String[] attendees, Employee employee) {
    meetingService.sendMeeting("Welcome-встреча", attendees, employee, employee.getEmploymentDate());
    meetingService.sendMeeting("Промежуточная встреча", attendees, employee, employee.getInterimDate());
    meetingService.sendMeeting("Итоговая встреча", attendees, employee, employee.getFinalDate());
  }

  private void taskListMail(Employee employee) {
    TaskForm taskForm = taskService.createTaskForm(employee);
    String userName = employee.getSelf().getFirstName() + " " + employee.getSelf().getLastName();
    Map<String, String> params = new HashMap<>();
    params.put("{{userName}}", userName);
    params.put("{{url}}", String.format(addTaskLink, taskForm.getKey()));
    mailService.sendMail(
        employee.getChief().getEmail(),
        "chief_missions.html",
        String.format("Задачи на испытательный срок (%s).", userName),
        params);
  }

}
