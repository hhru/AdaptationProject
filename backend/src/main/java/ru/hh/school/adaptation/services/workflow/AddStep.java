package ru.hh.school.adaptation.services.workflow;

import org.apache.commons.lang3.time.DateUtils;
import ru.hh.nab.core.util.FileSettings;

import ru.hh.school.adaptation.entities.PersonalInfo;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.entities.TaskForm;
import ru.hh.school.adaptation.misc.CommonUtils;
import ru.hh.school.adaptation.services.MailService;
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
  private TaskService taskService;
  private ScheduledMailService scheduledMailService;

  public AddStep(FileSettings fileSettings, MailService mailService, TaskService taskService, ScheduledMailService scheduledMailService) {
    this.mailService = mailService;
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
      mailService.sendMeeting("Welcome-встреча", attendees, attendees, employee, CommonUtils.dateConverter(employee.getEmploymentDate()), 30);
      mailService.sendMeeting("Промежуточная встреча", attendees, attendees, employee, CommonUtils.dateConverter(employee.getInterimDate()), 30);
      mailService.sendMeeting("Итоговая встреча", attendees, attendees, employee, CommonUtils.dateConverter(employee.getFinalDate()), 30);
  }

  private void taskListMail(Employee employee) {
    TaskForm taskForm = taskService.createTaskForm(employee);
    Map<String, String> params = new HashMap<>();
    params.put("{{userName}}", employee.getSelf().getFirstName() + " " + employee.getSelf().getLastName());
    params.put("{{url}}", String.format(addTaskLink, taskForm.getKey()));
    mailService.sendMail(employee.getChief().getEmail(), "chief_missions.html", "Задачи на испытательный срок", params);
  }

}
