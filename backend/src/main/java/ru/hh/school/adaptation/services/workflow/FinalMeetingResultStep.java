package ru.hh.school.adaptation.services.workflow;

import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.services.MailService;

import java.util.HashMap;
import java.util.Map;

public class FinalMeetingResultStep {
  private MailService mailService;

  public FinalMeetingResultStep(MailService mailService) {
    this.mailService = mailService;
  }

  public void onFinalMeetingResult(Employee employee) {
    Map<String, String> params = new HashMap<>();
    params.put("{{userName}}", employee.getSelf().getFirstName() + " " + employee.getSelf().getLastName());
    params.put("{{meetingType}}", "итоговая");
    params.put("{{url}}", "http://results.com");
    mailService.sendMail(employee.getHr().getSelf().getEmail(), "meeting_results", params);
  }
}
