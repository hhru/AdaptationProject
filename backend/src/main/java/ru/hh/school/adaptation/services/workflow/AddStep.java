package ru.hh.school.adaptation.services.workflow;

import org.apache.commons.lang3.time.DateUtils;
import ru.hh.nab.core.util.FileSettings;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.entities.TaskForm;
import ru.hh.school.adaptation.services.MailService;
import ru.hh.school.adaptation.services.ScheduledMailService;
import ru.hh.school.adaptation.services.TaskService;

import javax.inject.Singleton;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    String hrEmail = employee.getHr().getSelf().getEmail();

    Date d1 = employee.getEmploymentDate();
    Date d2 = DateUtils.addDays(DateUtils.addMonths(d1, 1), 15);
    Date d3 = DateUtils.addMonths(d1, 3);

    mailService.sendCalendar(hrEmail, "Welcome-встреча", dateFormat.format(d1));
    mailService.sendCalendar(hrEmail, "Промежуточная встреча", dateFormat.format(d2));
    mailService.sendCalendar(hrEmail, "Итоговая встреча", dateFormat.format(d3));
  }

  private void taskListMail(Employee employee) {
    TaskForm taskForm = taskService.createTaskForm(employee);
    Map<String, String> params = new HashMap<>();
    params.put("{{userName}}", employee.getSelf().getFirstName() + " " + employee.getSelf().getLastName());
    params.put("{{url}}", String.format(addTaskLink, taskForm.getKey()));
    mailService.sendMail(employee.getChief().getEmail(), "chief_missions", params);
  }

}
