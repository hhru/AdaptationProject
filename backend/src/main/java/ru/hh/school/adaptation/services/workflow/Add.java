package ru.hh.school.adaptation.services.workflow;

import ru.hh.school.adaptation.dao.MailTemplateDao;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.entities.Gender;
import ru.hh.school.adaptation.services.MailService;

import javax.inject.Singleton;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Singleton
public class Add {
  private MailService mailService;
  private MailTemplateDao mailTemplateDao;
  private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
  private boolean demo = false;

  public Add(MailService mailService, MailTemplateDao mailTemplateDao) {
    this.mailService = mailService;
    this.mailTemplateDao = mailTemplateDao;
  }

  public void onAdd(Employee employee) {
    taskListMail(employee);

    long delay = (employee.getEmploymentDate().getTime() - new Date().getTime())/1000;
    delay = demo ? 0 : delay;
    scheduledExecutorService.schedule(() -> welcomeMail(employee), delay, TimeUnit.SECONDS);

    welcomeMeetingNotify(employee);

    delay += (1.5 * 30 - 2 * 7) * 24 * 60 * 60;
    delay = demo ? 0 : delay;
    scheduledExecutorService.schedule(() -> interimMeetingNotify(employee), delay, TimeUnit.SECONDS);

    delay += 1.5 * 30 * 24 * 60 * 60;
    delay = demo ? 0 : delay;
    scheduledExecutorService.schedule(() -> finalMeetingNotify(employee), delay, TimeUnit.SECONDS);
  }

  private void taskListMail(Employee employee) {
    Map<String, String> params = new HashMap<>();
    params.put("{{userName}}", employee.getSelf().getFirstName() + " " + employee.getSelf().getLastName());
    params.put("{{url}}", "http://missions.com");
    mailService.sendMail(employee.getChief().getEmail(),
          mailService.applyTemplate(mailTemplateDao.getRecordByName("chief_missions").getHtml(), params), "Задачи на испытательный срок");
  }

  private void welcomeMail(Employee employee) {
    Map<String, String> params = new HashMap<>();
    params.put("{{userName}}", employee.getSelf().getFirstName());
    params.put("{{isMale}}", employee.getGender() == Gender.MALE ? "провел" : "провела");
    mailService.sendMail(employee.getSelf().getEmail(),
          mailService.applyTemplate(mailTemplateDao.getRecordByName("welcome").getHtml(), params), "Приветственное письмо");
  }

  private void welcomeMeetingNotify(Employee employee) {
    Map<String, String> params = new HashMap<>();
    params.put("{{userName}}", employee.getSelf().getFirstName() + " " + employee.getSelf().getLastName());
    params.put("{{meetingType}}", "welcome-встречи");
    mailService.sendMail(employee.getHr().getSelf().getEmail(),
          mailService.applyTemplate(mailTemplateDao.getRecordByName("meeting_room").getHtml(), params), "Забронировать переговорку");
  }

  private void interimMeetingNotify(Employee employee) {
    Map<String, String> params = new HashMap<>();
    params.put("{{userName}}", employee.getSelf().getFirstName() + " " + employee.getSelf().getLastName());
    params.put("{{meetingType}}", "промежуточной встречи");
    mailService.sendMail(employee.getHr().getSelf().getEmail(),
           mailService.applyTemplate(mailTemplateDao.getRecordByName("meeting_room").getHtml(), params), "Забронировать переговорку");
  }

  private void finalMeetingNotify(Employee employee) {
    Map<String, String> params = new HashMap<>();
    params.put("{{userName}}", employee.getSelf().getFirstName() + " " + employee.getSelf().getLastName());
    params.put("{{meetingType}}", "итоговой встречи");
    mailService.sendMail(employee.getHr().getSelf().getEmail(),
          mailService.applyTemplate(mailTemplateDao.getRecordByName("meeting_room").getHtml(), params), "Забронировать переговорку");
  }

}
