package ru.hh.school.adaptation.services.documents;

import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.misc.CommonUtils;


public class ProbationResultDocumentGenerator extends TaskDocumentGenerator {

  public ProbationResultDocumentGenerator() {
    super("probation_result.docx");
  }

  @Override
  protected String getDocumentName(Employee employee) {
    return "Итоговый отчет - " + CommonUtils.makeFioFromPersonalInfo(employee.getSelf());
  }

}
