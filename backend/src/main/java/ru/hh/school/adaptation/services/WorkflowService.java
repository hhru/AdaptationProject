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

import ru.hh.school.adaptation.services.workflow.AddStep;
import ru.hh.school.adaptation.services.workflow.TaskListStep;
import ru.hh.school.adaptation.services.workflow.WelcomeMeetingStep;
import ru.hh.school.adaptation.services.workflow.InterimMeeteingStep;
import ru.hh.school.adaptation.services.workflow.InterimMeetingResultStep;
import ru.hh.school.adaptation.services.workflow.FinalMeetingStep;
import ru.hh.school.adaptation.services.workflow.FinalMeetingResultStep;
import ru.hh.school.adaptation.services.workflow.QuestionnaireStep;

@Singleton
public class WorkflowService {
  private static final Logger logger = LoggerFactory.getLogger(WorkflowService.class);
  private CookieManager cookieManager = new CookieManager();
  private String host;

  private AddStep addStep;
  private TaskListStep taskListStep;
  private WelcomeMeetingStep welcomeMeetingStep;
  private InterimMeeteingStep interimMeeteingStep;
  private InterimMeetingResultStep interimMeetingResultStep;
  private FinalMeetingStep finalMeetingStep;
  private FinalMeetingResultStep finalMeetingResultStep;
  private QuestionnaireStep questionnaireStep;

  public WorkflowService(FileSettings fileSettings, AddStep addStep, TaskListStep taskListStep, WelcomeMeetingStep welcomeMeetingStep,
                         InterimMeeteingStep interimMeeteingStep, InterimMeetingResultStep interimMeetingResultStep,
                         FinalMeetingStep finalMeetingStep, FinalMeetingResultStep finalMeetingResultStep, QuestionnaireStep questionnaireStep) {
    this.addStep = addStep;
    this.taskListStep = taskListStep;
    this.welcomeMeetingStep = welcomeMeetingStep;
    this.interimMeeteingStep = interimMeeteingStep;
    this.interimMeetingResultStep = interimMeetingResultStep;
    this.finalMeetingStep = finalMeetingStep;
    this.finalMeetingResultStep = finalMeetingResultStep;
    this.questionnaireStep = questionnaireStep;

    Properties properties = fileSettings.getProperties();
    host = properties.getProperty("jira.host");
    jiraAuth(properties.getProperty("jira.username"), properties.getProperty("jira.password"));
  }

  public void stepAction(Employee employee, WorkflowStepType workflowStepType) {
    switch (workflowStepType) {
      case ADD:
        addStep.onAdd(employee);
        break;
      case TASK_LIST:
        taskListStep.onTaskList();
        break;
      case WELCOME_MEETING:
        welcomeMeetingStep.onWelcomeMeeting();
        break;
      case INTERIM_MEETING:
        interimMeeteingStep.onInterimMeeting();
        break;
      case INTERIM_MEETING_RESULT:
        interimMeetingResultStep.onInterimMeetingResult(employee);
        break;
      case FINAL_MEETING:
        finalMeetingStep.onFinalMeeting();
        break;
      case FINAL_MEETING_RESULT:
        finalMeetingResultStep.onFinalMeetingResult(employee);
        break;
      case QUESTIONNAIRE:
        questionnaireStep.onQuestionnaire(employee, cookieManager);
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
