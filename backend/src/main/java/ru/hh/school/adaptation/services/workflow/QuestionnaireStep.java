package ru.hh.school.adaptation.services.workflow;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import ru.hh.nab.core.util.FileSettings;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.entities.Gender;
import ru.hh.school.adaptation.entities.Log;
import ru.hh.school.adaptation.entities.Questionnaire;
import ru.hh.school.adaptation.services.CommentService;
import ru.hh.school.adaptation.services.MailService;
import ru.hh.school.adaptation.services.QuestionnaireService;
import ru.hh.school.adaptation.services.TransitionService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class QuestionnaireStep {
  private static final String addTaskLink = "https://adaptation.host/questionnaire/%s";

  private CommentService commentService;
  private MailService mailService;
  private TransitionService transitionService;
  private QuestionnaireService questionnaireService;
  private String projectKey;
  private String issueType;
  private String assignee;
  private static final Logger logger = LoggerFactory.getLogger(QuestionnaireStep.class);
  private String host;

  public QuestionnaireStep(FileSettings fileSettings, MailService mailService, @Lazy TransitionService transitionService,
                           CommentService commentService, QuestionnaireService questionnaireService) {
    this.mailService = mailService;
    this.transitionService = transitionService;
    this.commentService = commentService;
    this.questionnaireService = questionnaireService;

    Properties properties = fileSettings.getProperties();
    projectKey = properties.getProperty("jira.project.key");
    issueType = properties.getProperty("jira.issuetype");
    assignee = properties.getProperty("jira.assignee");
    host = properties.getProperty("jira.host");
  }

  public void onQuestionnaire(Employee employee, CookieManager cookieManager) {
    Questionnaire questionnaire = questionnaireService.createQuestionnaire(employee);
    Map<String, String> params = new HashMap<>();
    params.put("{{url}}", String.format(addTaskLink, questionnaire.getKey()));
    mailService.sendMail(employee.getChief().getEmail(), "questionnaire", params);

    Log log = new Log();
    log.setEmployee(employee);
    log.setAuthor("Система");
    log.setMessage("Сотруднику отправлен опросник новичка.");
    log.setLink(String.format("https://www.adaptation.host/api/employee/%d/questionnaire", employee.getId()));
    log.setEventDate(new Date());
    commentService.createLog(log);

    String jiraParams = new JSONObject()
          .put("fields", new JSONObject()
            .put("project", new JSONObject().put("key", projectKey))
            .put("summary", "Сделать ДМС")
            .put("description", "К нашей компании присоеденился новый сотрудник " + employee.getSelf().getFirstName() + " " +
              employee.getSelf().getLastName() + ". Для " + (employee.getGender()==Gender.MALE?"него":"нее") +
              " необходимо оформить ДМС.")
            .put("issuetype", new JSONObject().put("name", issueType))
            .put("assignee", new JSONObject().put("name", assignee))
          ).toString();
    createJiraTask(jiraParams, cookieManager);
    transitionService.setEmployeeNextTransition(employee);
  }

  private void createJiraTask(String params, CookieManager cookieManager) {
    HttpURLConnection connection = null;

    try {
      URL url = new URL(host + "/rest/api/latest/issue");
      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setRequestProperty("Cookie", StringUtils.join(cookieManager.getCookieStore().getCookies(), ";"));
      connection.setUseCaches(false);
      connection.setDoOutput(true);

      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
      writer.write(params);
      writer.flush();
      writer.close();

      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      JSONObject response = new JSONObject(reader.readLine());
      reader.close();
      logger.info("Create jira task with key {}", response.get("key"));
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      if (connection != null) {
        connection.disconnect();
      }
    }
  }
}
