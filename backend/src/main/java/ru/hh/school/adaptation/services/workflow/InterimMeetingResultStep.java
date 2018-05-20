package ru.hh.school.adaptation.services.workflow;

import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.services.MailService;

public class InterimMeetingResultStep {
  private MailService mailService;

  public InterimMeetingResultStep(MailService mailService) {
    this.mailService = mailService;
  }

  public void onInterimMeetingResult(Employee employee) {
  }
}
