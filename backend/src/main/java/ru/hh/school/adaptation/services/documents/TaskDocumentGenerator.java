package ru.hh.school.adaptation.services.documents;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.xmlbeans.XmlException;
import ru.hh.nab.core.util.FileSettings;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.entities.Task;
import ru.hh.school.adaptation.misc.CommonUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class TaskDocumentGenerator extends DocumentGenerator {

  public TaskDocumentGenerator(FileSettings fileSettings, String propertyName) {
    super(fileSettings, propertyName);
  }

  private void addTasksRows(List<Task> taskList, XWPFTable table, int startFromId) throws IOException, XmlException {
    String[][] recs = new String[taskList.size()][4];
    for (int i = 0; i < taskList.size(); i++) {
      recs[i][0] = Integer.toString(i + 1);
      recs[i][1] = taskList.get(i).getText();
      recs[i][2] = taskList.get(i).getDeadlineDate().toString();
      recs[i][3] = taskList.get(i).getResources();
    }

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

    return replacements;
  }

  public void fillDocWithData(Employee employee, XWPFDocument doc) throws XmlException, IOException {
    for (XWPFTable table : doc.getTables()) {
      for (int i = 0; i < table.getRows().size(); i++) {
        for (XWPFTableCell cell : table.getRow(i).getTableCells()) {
          if (cell.getText().contains("{{functional.tasks}}")) {
            addTasksRows(employee.getTaskForm().getTasks(), table, i + 1);
          }
          replaceInParagraphs(constructReplacements(employee), cell.getParagraphs());
        }
      }
    }
  }

}
