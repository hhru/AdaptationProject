package ru.hh.school.adaptation.services.documents;

import ru.hh.nab.core.util.FileSettings;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.misc.CommonUtils;


public class ProbationResultDocumentGenerator extends TaskDocumentGenerator {

  public ProbationResultDocumentGenerator(FileSettings fileSettings) {
    super(fileSettings, "document.template.probation-result");
  }

  @Override
  protected String getDocumentName(Employee employee) {
    return "Итоговый отчет - " + CommonUtils.makeFioFromPersonalInfo(employee.getSelf());
  }

}
