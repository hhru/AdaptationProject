package ru.hh.school.adaptation.services.documents;

import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.misc.CommonUtils;

public class ChiefTaskDocumentGenerator extends TaskDocumentGenerator {

  public ChiefTaskDocumentGenerator() {
    super("task_template.docx");
  }

  @Override
  protected String getDocumentName(Employee employee) {
    return "Задачи на ИС - " + CommonUtils.makeFioFromPersonalInfo(employee.getSelf());
  }
}
