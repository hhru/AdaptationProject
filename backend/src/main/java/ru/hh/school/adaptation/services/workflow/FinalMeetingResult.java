package ru.hh.school.adaptation.services.workflow;

import ru.hh.school.adaptation.dao.MailTemplateDao;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.services.MailService;

import java.util.HashMap;
import java.util.Map;

public class FinalMeetingResult {
  private MailService mailService;
  private MailTemplateDao mailTemplateDao;

  public FinalMeetingResult(MailService mailService, MailTemplateDao mailTemplateDao) {
    this.mailService = mailService;
    this.mailTemplateDao = mailTemplateDao;
  }

  public void onFinalMeetingResult(Employee employee) {
    Map<String, String> params = new HashMap<>();
    params.put("{{userName}}", employee.getSelf().getFirstName() + " " + employee.getSelf().getLastName());
    params.put("{{meetingType}}", "итоговая");
    params.put("{{url}}", "http://results.com");
    mailService.sendMail(employee.getHr().getSelf().getEmail(),
          mailService.applyTemplate(mailTemplateDao.getRecordByName("meeting_results").getHtml(), params), "Результаты встречи");
  }
}
