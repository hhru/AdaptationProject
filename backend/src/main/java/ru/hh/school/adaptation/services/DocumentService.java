package ru.hh.school.adaptation.services;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.PositionInParagraph;
import org.apache.poi.xwpf.usermodel.TextSegement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
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

public class DocumentService {

  private String taskDocumentPath;

  public DocumentService(FileSettings fileSettings) {
    this.taskDocumentPath = System.getProperty("settingsDir") + "/" + fileSettings.getString("document.template.task");
  }

  private long replaceInParagraphs(Map<String, String> replacements, List<XWPFParagraph> xwpfParagraphs) {
    long count = 0;
    for (XWPFParagraph paragraph : xwpfParagraphs) {
      List<XWPFRun> runs = paragraph.getRuns();

      for (Map.Entry<String, String> replPair : replacements.entrySet()) {
        String find = replPair.getKey();
        String repl = replPair.getValue();
        TextSegement found = paragraph.searchText(find, new PositionInParagraph());
        if (found != null) {
          count++;
          if (found.getBeginRun() == found.getEndRun()) {
            // Вся искомая строка в пределах одного курсора (XWPFRun)
            XWPFRun run = runs.get(found.getBeginRun());
            String runText = run.getText(run.getTextPosition());
            String replaced = runText.replace(find, repl);
            run.setText(replaced, 0);
          } else {
            // Искомая строка находится в нескольких курсорах - собираем их вместе
            StringBuilder b = new StringBuilder();
            for (int runPos = found.getBeginRun(); runPos <= found.getEndRun(); runPos++) {
              XWPFRun run = runs.get(runPos);
              b.append(run.getText(run.getTextPosition()));
            }
            String connectedRuns = b.toString();
            String replaced = connectedRuns.replace(find, repl);

            // Передаем в курсор строку со всех остальных курсоров
            XWPFRun partOne = runs.get(found.getBeginRun());
            partOne.setText(replaced, 0);
            // Удаляем старый текст из остальных курсоров
            for (int runPos = found.getBeginRun() + 1; runPos <= found.getEndRun(); runPos++) {
              XWPFRun partNext = runs.get(runPos);
              partNext.setText("", 0);
            }
          }
        }
      }
    }
    return count;
  }

  private void addTasksRows(List<Task> taskList, XWPFTable table, int startFromId) throws IOException, XmlException {
    String[][] recs = new String[taskList.size()][4];
    for (int i = 0; i < taskList.size(); i++) {
      recs[i][0] = Integer.toString(i + 1);
      recs[i][1] = taskList.get(i).getText();
      recs[i][2] = taskList.get(i).getDeadlineDate().toString();
      recs[i][3] = taskList.get(i).getResources();
    }

    XWPFTableRow rowTemplate = table.getRow(startFromId);
    for (int i = 0; i < recs.length; i++) {
      CTRow ctrow = CTRow.Factory.parse(rowTemplate.getCtRow().newInputStream());
      XWPFTableRow newRow = new XWPFTableRow(ctrow, table);
      for (int j = 0; j < recs[i].length; j++) {
        for (XWPFParagraph paragraph : newRow.getTableCells().get(j).getParagraphs()) {
          XWPFRun run = paragraph.insertNewRun(0);
          if (j >= 2) {
            run.setItalic(true);
          }
          run.setText(recs[i][j], 0);
        }
      }
      table.addRow(newRow, startFromId + i + 1);
    }
    table.removeRow(startFromId);
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

  public Named<byte[]> generateTaskDoc(Employee employee) throws IOException, InvalidFormatException, XmlException {
    XWPFDocument doc = new XWPFDocument(OPCPackage.open(taskDocumentPath));

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
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    doc.write(outputStream);
    outputStream.close();
    return new Named<>(CommonUtils.makeFioFromPersonalInfo(employee.getSelf()), outputStream.toByteArray());
  }


}
