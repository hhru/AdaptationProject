package ru.hh.school.adaptation.services.workflow;

import org.apache.commons.lang3.time.DateUtils;
import ru.hh.school.adaptation.entities.Log;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.entities.Gender;
import ru.hh.school.adaptation.entities.TaskForm;
import ru.hh.school.adaptation.services.CommentService;
import ru.hh.school.adaptation.services.MailService;
import ru.hh.school.adaptation.services.TaskService;

import javax.inject.Singleton;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Singleton
public class AddStep {
  private static final String addTaskLink = "https://adaptation.host/add_tasks/%s";
  private boolean demo = true;

  private MailService mailService;
  private TaskService taskService;
  private CommentService commentService;
  private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

  public AddStep(MailService mailService, TaskService taskService, CommentService commentService) {
    this.mailService = mailService;
    this.taskService = taskService;
    this.commentService = commentService;
  }

  public void onAdd(Employee employee) {
    String hrEmail = employee.getHr().getSelf().getEmail();

    DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    Date d1 = employee.getEmploymentDate();
    Date d2 = DateUtils.addMonths(d1, 1);
    d2 = DateUtils.addDays(d2, 15);
    Date d3 = DateUtils.addMonths(d1, 3);

    mailService.sendCalendar(hrEmail, "Welcome-встреча", dateFormat.format(d1));
    mailService.sendCalendar(hrEmail, "Промежуточная встреча", dateFormat.format(d2));
    mailService.sendCalendar(hrEmail, "Итоговая встреча", dateFormat.format(d3));

    scheduledExecutorService.schedule(() -> taskListMail(employee), 1, TimeUnit.SECONDS);

    long delay = (employee.getEmploymentDate().getTime() - new Date().getTime())/1000;
    delay = demo ? 3 : delay;
    scheduledExecutorService.schedule(() -> welcomeMail(employee), delay, TimeUnit.SECONDS);

    scheduledExecutorService.schedule(() -> welcomeMeetingNotify(employee), 5, TimeUnit.SECONDS);

    delay += (1.5 * 30 - 2 * 7) * 24 * 60 * 60;
    delay = demo ? 7 : delay;
    scheduledExecutorService.schedule(() -> interimMeetingNotify(employee), delay, TimeUnit.SECONDS);

    delay += 1.5 * 30 * 24 * 60 * 60;
    delay = demo ? 10 : delay;
    scheduledExecutorService.schedule(() -> finalMeetingNotify(employee), delay, TimeUnit.SECONDS);
  }

  private void taskListMail(Employee employee) {
    TaskForm taskForm = taskService.createTaskForm(employee);
    Map<String, String> params = new HashMap<>();
    params.put("{{userName}}", employee.getSelf().getFirstName() + " " + employee.getSelf().getLastName());
    params.put("{{url}}", String.format(addTaskLink, taskForm.getKey()));
    mailService.sendMail(employee.getChief().getEmail(), "chief_missions", params);
  }

  private void welcomeMail(Employee employee) {
    Map<String, String> params = new HashMap<>();
    params.put("{{userName}}", employee.getSelf().getFirstName());
    params.put("{{isMale}}", employee.getGender() == Gender.MALE ? "провел" : "провела");
    mailService.sendMail(employee.getSelf().getEmail(), "welcome", params);

    Log log = new Log();
    log.setEmployee(employee);
    log.setAuthor("Adaptation");
    log.setMessage("Сотруднику отправлено welcome-письмо");
    log.setEventDate(new Date());
    commentService.createLog(log);
  }

  private void welcomeMeetingNotify(Employee employee) {
    Map<String, String> params = new HashMap<>();
    params.put("{{userName}}", employee.getSelf().getFirstName() + " " + employee.getSelf().getLastName());
    params.put("{{meetingType}}", "welcome-встречи");
    mailService.sendMail(employee.getHr().getSelf().getEmail(), "meeting_room", params);
  }

  private void interimMeetingNotify(Employee employee) {
    Map<String, String> params = new HashMap<>();
    params.put("{{userName}}", employee.getSelf().getFirstName() + " " + employee.getSelf().getLastName());
    params.put("{{meetingType}}", "промежуточной встречи");
    mailService.sendMail(employee.getHr().getSelf().getEmail(), "meeting_room", params);
  }

  private void finalMeetingNotify(Employee employee) {
    Map<String, String> params = new HashMap<>();
    params.put("{{userName}}", employee.getSelf().getFirstName() + " " + employee.getSelf().getLastName());
    params.put("{{meetingType}}", "итоговой встречи");
    mailService.sendMail(employee.getHr().getSelf().getEmail(), "meeting_room", params);
  }

}
