package ru.hh.school.adaptation.services.workflow;

import org.apache.commons.lang3.time.DateUtils;
import ru.hh.nab.core.util.FileSettings;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.entities.TaskForm;
import ru.hh.school.adaptation.services.MailService;
import ru.hh.school.adaptation.services.ScheduledMailService;
import ru.hh.school.adaptation.services.TaskService;

import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class AddStep {

  private String addTaskLink;

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
    sendCalendar(employee);
    taskListMail(employee);
    scheduledMailService.scheduleNewMail(employee.getId(), DateUtils.addHours(employee.getEmploymentDate(), 8));
  }

  private void sendCalendar(Employee employee) {
    String hrEmail = employee.getHr().getSelf().getEmail();
    LocalDateTime welcomeDate = LocalDateTime.of(
        employee.getEmploymentDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
        LocalTime.of(13, 0)
    );
    mailService.sendCalendar(hrEmail, "Welcome-встреча", welcomeDate, 30);
    mailService.sendCalendar(hrEmail, "Промежуточная встреча", welcomeDate.plusMonths(1).plusDays(15), 30);
    mailService.sendCalendar(hrEmail, "Итоговая встреча", welcomeDate.plusMonths(3), 30);
  }

  private void taskListMail(Employee employee) {
    TaskForm taskForm = taskService.createTaskForm(employee);
    Map<String, String> params = new HashMap<>();
    params.put("{{userName}}", employee.getSelf().getFirstName() + " " + employee.getSelf().getLastName());
    params.put("{{url}}", String.format(addTaskLink, taskForm.getKey()));
    mailService.sendMail(employee.getChief().getEmail(), "chief_missions.html", "Задачи на испытательный срок", params);
  }

}
