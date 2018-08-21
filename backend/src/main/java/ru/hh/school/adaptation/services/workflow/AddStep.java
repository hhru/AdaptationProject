package ru.hh.school.adaptation.services.workflow;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.context.annotation.Lazy;
import ru.hh.nab.core.util.FileSettings;
import ru.hh.school.adaptation.entities.Log;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.entities.Gender;
import ru.hh.school.adaptation.entities.TaskForm;
import ru.hh.school.adaptation.services.CommentService;
import ru.hh.school.adaptation.services.EmployeeService;
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

  private String addTaskLink;

  private MailService mailService;
  private TaskService taskService;
  private EmployeeService employeeService;
  private CommentService commentService;
  private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

  public AddStep(FileSettings fileSettings, MailService mailService, TaskService taskService,
                 CommentService commentService, @Lazy EmployeeService employeeService) {
    this.mailService = mailService;
    this.taskService = taskService;
    this.commentService = commentService;
    this.employeeService = employeeService;

    addTaskLink = "https://" + fileSettings.getProperties().getProperty("adaptation.host") + "/add_tasks/%s";
  }

  public void onAdd(Employee employee) {
    sendCalendar(employee);

    scheduledExecutorService.schedule(() -> taskListMail(employee), 1, TimeUnit.SECONDS);

    long delay;
    if (employee.getEmploymentDate().after(new Date())) {
      delay = (employee.getEmploymentDate().getTime() - new Date().getTime())/1000;
      scheduledExecutorService.schedule(() -> welcomeMail(employee), delay, TimeUnit.SECONDS);
    } else if (new SimpleDateFormat("ddMMyyyy").format(new Date()).equals(new SimpleDateFormat("ddMMyyyy").format(employee.getEmploymentDate()))) {
      delay = 1;
      scheduledExecutorService.schedule(() -> welcomeMail(employee), delay, TimeUnit.SECONDS);
    }
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

  private void welcomeMail(Employee employee) {
    Map<String, String> params = new HashMap<>();
    params.put("{{userName}}", employee.getSelf().getFirstName());
    params.put("{{gender_provel}}", employee.getGender() == Gender.MALE ? "провел" : "провела");
    params.put("{{gender_samomu}}", employee.getGender() == Gender.MALE ? "самому" : "самой");
    params.put("{{gender_osvoilsya}}", employee.getGender() == Gender.MALE ? "освоился" : "освоилась");
    params.put("{{gender_smog}}", employee.getGender() == Gender.MALE ? "смог" : "смогла");
    mailService.sendMail(employee.getSelf().getEmail(), "welcome2", params);

    Log log = new Log();
    log.setEmployee(employee);
    log.setAuthor("Adaptation");
    log.setMessage("Сотруднику отправлено welcome-письмо");
    log.setEventDate(new Date());
    commentService.createLog(log);
  }

  private void welcomeMeetingNotify(Employee employee) {
    Employee employeeCurrent = employeeService.getEmployee(employee.getId());
    Map<String, String> params = new HashMap<>();
    params.put("{{userName}}", employeeCurrent.getSelf().getFirstName() + " " + employeeCurrent.getSelf().getLastName());
    params.put("{{meetingType}}", "welcome-встречи");
    mailService.sendMail(employeeCurrent.getHr().getSelf().getEmail(), "meeting_room", params);
  }

  private void interimMeetingNotify(Employee employee) {
    Employee employeeCurrent = employeeService.getEmployee(employee.getId());
    Map<String, String> params = new HashMap<>();
    params.put("{{userName}}", employeeCurrent.getSelf().getFirstName() + " " + employeeCurrent.getSelf().getLastName());
    params.put("{{meetingType}}", "промежуточной встречи");
    mailService.sendMail(employeeCurrent.getHr().getSelf().getEmail(), "meeting_room", params);
  }

  private void finalMeetingNotify(Employee employee) {
    Employee employeeCurrent = employeeService.getEmployee(employee.getId());
    Map<String, String> params = new HashMap<>();
    params.put("{{userName}}", employeeCurrent.getSelf().getFirstName() + " " + employeeCurrent.getSelf().getLastName());
    params.put("{{meetingType}}", "итоговой встречи");
    mailService.sendMail(employeeCurrent.getHr().getSelf().getEmail(), "meeting_room", params);
  }

}
