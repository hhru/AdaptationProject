package ru.hh.school.adaptation.services;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.nab.core.util.FileSettings;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.entities.WorkflowStepType;

import java.io.DataOutputStream;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import javax.inject.Singleton;

import ru.hh.school.adaptation.services.workflow.Add;
import ru.hh.school.adaptation.services.workflow.TaskList;
import ru.hh.school.adaptation.services.workflow.WelcomeMeeting;
import ru.hh.school.adaptation.services.workflow.InterimMeeteing;
import ru.hh.school.adaptation.services.workflow.InterimMeetingResult;
import ru.hh.school.adaptation.services.workflow.FinalMeeting;
import ru.hh.school.adaptation.services.workflow.FinalMeetingResult;
import ru.hh.school.adaptation.services.workflow.Questionnaire;

@Singleton
public class WorkflowService {
  private Add add;
  private TaskList taskList;
  private WelcomeMeeting welcomeMeeting;
  private InterimMeeteing interimMeeteing;
  private InterimMeetingResult interimMeetingResult;
  private FinalMeeting finalMeeting;
  private FinalMeetingResult finalMeetingResult;
  private Questionnaire questionnaire;

  private CookieManager cookieManager = new CookieManager();
  private String host;
  private static final Logger logger = LoggerFactory.getLogger(WorkflowService.class);

  public WorkflowService(FileSettings fileSettings, Add add, TaskList taskList, WelcomeMeeting welcomeMeeting, InterimMeeteing interimMeeteing,
                         InterimMeetingResult interimMeetingResult, FinalMeeting finalMeeting, FinalMeetingResult finalMeetingResult,
                         Questionnaire questionnaire) {
    this.add = add;
    this.taskList = taskList;
    this.welcomeMeeting = welcomeMeeting;
    this.interimMeeteing = interimMeeteing;
    this.interimMeetingResult = interimMeetingResult;
    this.finalMeeting = finalMeeting;
    this.finalMeetingResult = finalMeetingResult;
    this.questionnaire = questionnaire;

    Properties properties = fileSettings.getProperties();
    host = properties.getProperty("jira.host");
    jiraAuth(properties.getProperty("jira.username"), properties.getProperty("jira.password"));
  }

  public void stepAction(Employee employee, WorkflowStepType workflowStepType) {
    switch (workflowStepType) {
      case ADD:
        add.onAdd(employee);
        break;
      case TASK_LIST:
        taskList.onTaskList();
        break;
      case WELCOME_MEETING:
        welcomeMeeting.onWelcomeMeeting();
        break;
      case INTERIM_MEETING:
        interimMeeteing.onInterimMeeting();
        break;
      case INTERIM_MEETING_RESULT:
        interimMeetingResult.onInterimMeetingResult(employee);
        break;
      case FINAL_MEETING:
        finalMeeting.onFinalMeeting();
        break;
      case FINAL_MEETING_RESULT:
        finalMeetingResult.onFinalMeetingResult(employee);
        break;
      case QUESTIONNAIRE:
        questionnaire.onQuestionnaire(employee, cookieManager);
        break;
    }
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
}
