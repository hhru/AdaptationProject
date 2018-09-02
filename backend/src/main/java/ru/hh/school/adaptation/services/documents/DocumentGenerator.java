package ru.hh.school.adaptation.services.documents;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.PositionInParagraph;
import org.apache.poi.xwpf.usermodel.TextSegement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.misc.Named;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public abstract class DocumentGenerator {

  protected String documentPath;

  public DocumentGenerator(String templateName) {
    this.documentPath = System.getProperty("templatesDir") + "/doc/" + templateName;
  }

  protected long replaceInParagraphs(Map<String, String> replacements, List<XWPFParagraph> xwpfParagraphs) {
    long count = 0;
    for (XWPFParagraph paragraph : xwpfParagraphs) {
      List<XWPFRun> runs = paragraph.getRuns();

      for (Map.Entry<String, String> replPair : replacements.entrySet()) {
        String find = replPair.getKey();
        String repl = replPair.getValue();
        TextSegement found;
        try {
          found = paragraph.searchText(find, new PositionInParagraph());
        } catch (NullPointerException e) {
          continue;
        }
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

  protected void insertRowsInTable(XWPFTable table, int startFromId, String[][] recs) throws IOException, XmlException {
    XWPFTableRow rowTemplate = table.getRow(startFromId);
    for (int i = 0; i < recs.length; i++) {
      CTRow ctrow = CTRow.Factory.parse(rowTemplate.getCtRow().newInputStream());
      XWPFTableRow newRow = new XWPFTableRow(ctrow, table);
      for (int j = 0; j < recs[i].length; j++) {
        for (XWPFParagraph paragraph : newRow.getTableCells().get(j).getParagraphs()) {
          XWPFRun run = paragraph.insertNewRun(0);
          run.setText(recs[i][j], 0);
        }
      }
      table.addRow(newRow, startFromId + i + 1);
    }
    table.removeRow(startFromId);
  }

  public Named<byte[]> generateDoc(Employee employee) throws IOException, InvalidFormatException, XmlException {
    XWPFDocument doc = new XWPFDocument(OPCPackage.open(documentPath));
    fillDocWithData(employee, doc);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    doc.write(outputStream);
    outputStream.close();
    return new Named<>(getDocumentName(employee), outputStream.toByteArray());
  }

  protected abstract void fillDocWithData(Employee employee, XWPFDocument doc) throws IOException, XmlException;

  protected abstract String getDocumentName(Employee employee);

}
