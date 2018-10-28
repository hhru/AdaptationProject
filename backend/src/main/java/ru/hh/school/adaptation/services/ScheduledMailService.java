package ru.hh.school.adaptation.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.school.adaptation.dao.ScheduledMailDao;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.entities.Gender;
import ru.hh.school.adaptation.entities.Log;
import ru.hh.school.adaptation.entities.ScheduledMail;

@Singleton
public class ScheduledMailService {

  private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

  private ScheduledMailDao scheduledMailDao;
  private EmployeeService employeeService;
  private MailService mailService;
  private CommentService commentService;

  public ScheduledMailService(ScheduledMailDao scheduledMailDao, @Lazy EmployeeService employeeService, MailService mailService,
                              CommentService commentService) {
    this.scheduledMailDao = scheduledMailDao;
    this.employeeService = employeeService;
    this.mailService = mailService;
    this.commentService = commentService;
  }

  @Transactional
  public void scheduleAllMailFromDb() {
    scheduledMailDao.getAll().forEach(this::scheduleWelcomeMail);
  }

  @Transactional
  public void scheduleNewMail(Integer employeeId, Date date) {
    ScheduledMail scheduledMail = new ScheduledMail();
    scheduledMail.setEmployeeId(employeeId);
    scheduledMail.setTriggerDate(date);
    scheduledMailDao.save(scheduledMail);

    scheduleWelcomeMail(scheduledMail);
  }

  private void scheduleWelcomeMail(ScheduledMail scheduledMail) {
    long delay = 1;
    if (scheduledMail.getTriggerDate().after(new Date())) {
      delay = (scheduledMail.getTriggerDate().getTime() - new Date().getTime())/1000;
    }
    scheduledExecutorService.schedule(() -> sendWelcomeMail(scheduledMail), delay, TimeUnit.SECONDS);
  }

  private void sendWelcomeMail(ScheduledMail scheduledMail) {
    Employee employee = employeeService.getEmployee(scheduledMail.getEmployeeId());

    Map<String, String> params = new HashMap<>();
    params.put("{{userName}}", employee.getSelf().getFirstName());
    params.put("{{gender_provel}}", employee.getGender() == Gender.MALE ? "провел" : "провела");
    params.put("{{gender_samomu}}", employee.getGender() == Gender.MALE ? "самому" : "самой");
    params.put("{{gender_osvoilsya}}", employee.getGender() == Gender.MALE ? "освоился" : "освоилась");
    params.put("{{gender_smog}}", employee.getGender() == Gender.MALE ? "смог" : "смогла");
    mailService.sendMail(employee.getSelf().getEmail(), "welcome_2.html", "Приветственное письмо", params);

    Log log = new Log();
    log.setEmployee(employee);
    log.setAuthor("Adaptation");
    log.setMessage("Сотруднику отправлено welcome-письмо");
    log.setEventDate(new Date());
    commentService.createLog(log);

    scheduledMailDao.delete(scheduledMail);
  }
}
