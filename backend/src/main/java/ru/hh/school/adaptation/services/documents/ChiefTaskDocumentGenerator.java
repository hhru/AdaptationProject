package ru.hh.school.adaptation.services.documents;

import ru.hh.nab.core.util.FileSettings;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.misc.CommonUtils;

public class ChiefTaskDocumentGenerator extends TaskDocumentGenerator {

  public ChiefTaskDocumentGenerator(FileSettings fileSettings) {
    super(fileSettings, "document.template.task");
  }

  @Override
  protected String getDocumentName(Employee employee) {
    return "Задачи на ИС - " + CommonUtils.makeFioFromPersonalInfo(employee.getSelf());
  }
}
