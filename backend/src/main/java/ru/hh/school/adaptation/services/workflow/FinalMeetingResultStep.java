package ru.hh.school.adaptation.services.workflow;

import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.services.MailService;

public class FinalMeetingResultStep {
  private MailService mailService;

  public FinalMeetingResultStep(MailService mailService) {
    this.mailService = mailService;
  }

  public void onFinalMeetingResult(Employee employee) {
  }
}
