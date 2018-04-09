package ru.hh.school.adaptation.services;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.nab.core.util.FileSettings;
import ru.hh.school.adaptation.dao.MailTemplateDao;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.entities.Gender;
import ru.hh.school.adaptation.entities.WorkflowStepType;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import org.springframework.context.annotation.Lazy;

@Singleton
public class WorkflowService {
  private MailService mailService;
  private TransitionService transitionService;
  private MailTemplateDao mailTemplateDao;
  private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
  private CookieManager cookieManager = new CookieManager();
  private static final Logger logger = LoggerFactory.getLogger(WorkflowService.class);
  private String host;
  private String projectKey;
  private String issueType;
  private String assignee;

  public WorkflowService(FileSettings fileSettings, MailService mailService,
                         @Lazy TransitionService transitionService, MailTemplateDao mailTemplateDao) {
    this.mailService = mailService;
    this.transitionService = transitionService;
    this.mailTemplateDao = mailTemplateDao;

    Properties properties = fileSettings.getProperties();
    host = properties.getProperty("jira.host");
    projectKey = properties.getProperty("jira.project.key");
    issueType = properties.getProperty("jira.issuetype");
    assignee = properties.getProperty("jira.assignee");
    jiraAuth(properties.getProperty("jira.username"), properties.getProperty("jira.password"));
  }

  public void stepAction(Employee employee, WorkflowStepType workflowStepType) {
    switch (workflowStepType) {
      case ADD:
        onAdd(employee);
        break;
      case TASK_LIST:
        onTaskList(employee);
        break;
      case WELCOME_MEETING:
        onWelcomeMeeting(employee);
        break;
      case INTERIM_MEETING:
        onInterimMeeting(employee);
        break;
      case INTERIM_MEETING_RESULT:
        onInterimMeetingResult(employee);
        break;
      case FINAL_MEETING:
        onFinalMeeting(employee);
        break;
      case FINAL_MEETING_RESULT:
        onFinalMeetingResult(employee);
        break;
      case QUESTIONNAIRE:
        onQuestionnaire(employee);
        break;
    }
  }

  private void onAdd(Employee employee) {
    long delay = (employee.getEmploymentDate().getTime() - new Date().getTime())/1000;
    scheduledExecutorService.schedule(() -> onAddSheduled(employee), delay, TimeUnit.SECONDS);
    Map<String, String> params = new HashMap<>();
    params.put("{{userName}}", employee.getSelf().getFirstName() + " " + employee.getSelf().getLastName());
    params.put("{{url}}", "http://missions.com");
    mailService.sendMail(employee.getChief().getEmail(),
            mailService.applyTemplate(mailTemplateDao.getRecordByName("chief_missions").getHtml(), params), "Задачи на испытательный срок");
  }

  private void onAddSheduled(Employee employee) {
    Map<String, String> params = new HashMap<>();
    params.put("{{userName}}", employee.getSelf().getFirstName());
    params.put("{{isMale}}", employee.getGender() == Gender.MALE ? "провел" : "провела");
    mailService.sendMail(employee.getSelf().getEmail(),
            mailService.applyTemplate(mailTemplateDao.getRecordByName("welcome").getHtml(), params), "Приветственное письмо");
    transitionService.setEmployeeNextTransition(employee);
  }

  private void onTaskList(Employee employee) {
    Map<String, String> params = new HashMap<>();
    params.put("{{userName}}", employee.getSelf().getFirstName() + " " + employee.getSelf().getLastName());
    params.put("{{missions}}", "1. Write cool code");
    mailService.sendMail(employee.getHr().getSelf().getEmail(),
            mailService.applyTemplate(mailTemplateDao.getRecordByName("hr_missions").getHtml(), params), "Задачи на испытательный срок");
  }

  private void onWelcomeMeeting(Employee employee) {
    Map<String, String> params = new HashMap<>();
    params.put("{{userName}}", employee.getSelf().getFirstName() + " " + employee.getSelf().getLastName());
    params.put("{{meetingType}}", "welcome-встречи");
    mailService.sendMail(employee.getHr().getSelf().getEmail(),
            mailService.applyTemplate(mailTemplateDao.getRecordByName("meeting_room").getHtml(), params), "Забронировать переговорку");
  }

  private void onInterimMeeting(Employee employee) {
    Map<String, String> params = new HashMap<>();
    params.put("{{userName}}", employee.getSelf().getFirstName() + " " + employee.getSelf().getLastName());
    params.put("{{meetingType}}", "промежуточной встречи");
    mailService.sendMail(employee.getHr().getSelf().getEmail(),
            mailService.applyTemplate(mailTemplateDao.getRecordByName("meeting_room").getHtml(), params), "Забронировать переговорку");
  }

  private void onInterimMeetingResult(Employee employee) {
    Map<String, String> params = new HashMap<>();
    params.put("{{userName}}", employee.getSelf().getFirstName() + " " + employee.getSelf().getLastName());
    params.put("{{meetingType}}", "промежуточная");
    params.put("{{url}}", "http://results.com");
    mailService.sendMail(employee.getHr().getSelf().getEmail(),
            mailService.applyTemplate(mailTemplateDao.getRecordByName("meeting_results").getHtml(), params), "Результаты встречи");
  }

  private void onFinalMeeting(Employee employee) {
    Map<String, String> params = new HashMap<>();
    params.put("{{userName}}", employee.getSelf().getFirstName() + " " + employee.getSelf().getLastName());
    params.put("{{meetingType}}", "итоговой встречи");
    mailService.sendMail(employee.getHr().getSelf().getEmail(),
            mailService.applyTemplate(mailTemplateDao.getRecordByName("meeting_room").getHtml(), params), "Забронировать переговорку");
  }

  private void onFinalMeetingResult(Employee employee) {
    Map<String, String> params = new HashMap<>();
    params.put("{{userName}}", employee.getSelf().getFirstName() + " " + employee.getSelf().getLastName());
    params.put("{{meetingType}}", "итоговая");
    params.put("{{url}}", "http://results.com");
    mailService.sendMail(employee.getHr().getSelf().getEmail(),
            mailService.applyTemplate(mailTemplateDao.getRecordByName("meeting_results").getHtml(), params), "Результаты встречи");
  }

  private void onQuestionnaire(Employee employee) {
    Map<String, String> params = new HashMap<>();
    params.put("{{url}}", "http://questionnaire.com");
    mailService.sendMail(employee.getSelf().getEmail(),
            mailService.applyTemplate(mailTemplateDao.getRecordByName("questionnaire").getHtml(), params), "Опросник для новичка");

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
    createJiraTask(jiraParams);

    transitionService.setEmployeeNextTransition(employee);
  }

  private void jiraAuth(String username, String password) {
    HttpURLConnection connection = null;

    try {
      URL url = new URL(host + "/rest/auth/latest/session");
      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setUseCaches(false);
      connection.setDoOutput(true);

      String parameters = new JSONObject()
              .put("username", username)
              .put("password", password)
              .toString();
      DataOutputStream out = new DataOutputStream(connection.getOutputStream());
      out.writeBytes(parameters);
      out.flush();
      out.close();

      String cookiesHeader = connection.getHeaderField("Set-Cookie");
      List<HttpCookie> cookies = HttpCookie.parse(cookiesHeader);
      cookies.forEach(cookie -> cookieManager.getCookieStore().add(null, cookie));

      logger.info("Jira authentication successful");
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      if (connection != null) {
        connection.disconnect();
      }
    }
  }

  private void createJiraTask(String params) {
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
