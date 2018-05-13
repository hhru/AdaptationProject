package ru.hh.school.adaptation.services.documents;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;
import ru.hh.nab.core.util.FileSettings;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.entities.Task;
import ru.hh.school.adaptation.misc.CommonUtils;
import ru.hh.school.adaptation.misc.Named;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProbationResultDocumentGenerator extends DocumentGenerator {

  public ProbationResultDocumentGenerator(FileSettings fileSettings) {
    super(fileSettings, "document.template.probation-result");
  }

  private void addTasksRows(List<Task> taskList, XWPFTable table, int startFromId) throws IOException, XmlException {
    String[][] recs = new String[taskList.size()][4];
    for (int i = 0; i < taskList.size(); i++) {
      recs[i][0] = Integer.toString(i + 1);
      recs[i][1] = taskList.get(i).getText();
      recs[i][2] = taskList.get(i).getDeadlineDate().toString();
      recs[i][3] = "Результат TODO"; //TODO
    }

    insertRowsInTable(table, startFromId, recs);
  }


  private void addProjectRows(XWPFTable table, int startFromId) throws IOException, XmlException {
    String[][] recs = new String[][]{
        {"1", "Сайт Headhunter", "Разработчик", "2 месяца", "Новый функционал"},
        {"2", "Внутренний документооборот", "Разработчик", "1 месяц", "Автоматизация заполнения отчетов"}
    };
    insertRowsInTable(table, startFromId, recs);
  }

  private void addCoursesRows(XWPFTable table, int startFromId) throws IOException, XmlException {
    String[][] recs = new String[][]{
        {"1", "Agile training", "blah-blah", "2018-02-02", "2018-02-05"},
        {"2", "Java Certification", "ja ja ja java", "2018-10-03", "2018-10-03"},
        {"3", "Мастер-класс по распределенным архитектурам", "asrttrvrvw 434v3 f3f34f !!", "2017-12-15", "2017-12-16"}
    };
    insertRowsInTable(table, startFromId, recs);
  }

  private Map<String, String> constructReplacements(Employee employee) {
    Map<String, String> replacements = new HashMap<>();
    String fio = CommonUtils.makeFioFromPersonalInfo(employee.getSelf());
    String chiefFio = CommonUtils.makeFioFromPersonalInfo(employee.getChief());
    String mentroFio = employee.getMentor() == null ? "" : CommonUtils.makeFioFromPersonalInfo(employee.getMentor());
    replacements.put("{{employee.fullName}}", fio);
    replacements.put("{{employee.employmentDate}}", employee.getEmploymentDate().toString());
    replacements.put("{{employee.endDate}}", DateUtils.addMonths(employee.getEmploymentDate(), 3).toString());
    replacements.put("{{employee.position}}", employee.getPosition());
    replacements.put("{{employee.chief}}", chiefFio);
    replacements.put("{{employee.mentor}}", mentroFio);

    replacements.put("{{functional.tasks}}", "");
    replacements.put("{{project.table}}", "");
    replacements.put("{{courses.table}}", "");

    replacements.put("{{recommendations}}", "");
    replacements.put("{{result.mark}}", "");
    replacements.put("{{result.comment}}", "");

    replacements.put("{{corptask1.result}}", "Ё!");
    replacements.put("{{corptask2.result}}", "МАЁ!");
    return replacements;
  }

  public void fillDocWithData(Employee employee, XWPFDocument doc) throws IOException, XmlException {
    for (XWPFTable table : doc.getTables()) {
      for (int i = 0; i < table.getRows().size(); i++) {
        for (XWPFTableCell cell : table.getRow(i).getTableCells()) {
          if (cell.getText().contains("{{functional.tasks}}")) {
            addTasksRows(employee.getTaskForm().getTasks(), table, i + 1);
          }
          if (cell.getText().contains("{{project.table}}")) {
            addProjectRows(table, i + 1);
          }
          if (cell.getText().contains("{{courses.table}}")) {
            addCoursesRows(table, i + 1);
          }
          replaceInParagraphs(constructReplacements(employee), cell.getParagraphs());
        }
      }
    }
  }

  @Override
  protected String getDocumentName(Employee employee) {
    return "Итоговый отчет - " + CommonUtils.makeFioFromPersonalInfo(employee.getSelf());
  }

}
