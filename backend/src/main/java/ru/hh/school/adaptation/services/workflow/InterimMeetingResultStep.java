package ru.hh.school.adaptation.services.workflow;

import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.services.MailService;

import java.util.HashMap;
import java.util.Map;

public class InterimMeetingResultStep {
  private MailService mailService;

  public InterimMeetingResultStep(MailService mailService) {
    this.mailService = mailService;
  }

  public void onInterimMeetingResult(Employee employee) {
    Map<String, String> params = new HashMap<>();
    params.put("{{userName}}", employee.getSelf().getFirstName() + " " + employee.getSelf().getLastName());
    params.put("{{meetingType}}", "промежуточная");
    params.put("{{url}}", "http://results.com");
    mailService.sendMail(employee.getHr().getSelf().getEmail(), "meeting_results", params);
  }
}
