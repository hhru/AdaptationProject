package ru.hh.school.adaptation.services;

import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.entities.WorkflowStepType;
import ru.hh.school.adaptation.services.workflow.AddStep;
import ru.hh.school.adaptation.services.workflow.TaskListStep;
import ru.hh.school.adaptation.services.workflow.WelcomeMeetingStep;
import ru.hh.school.adaptation.services.workflow.InterimMeeteingStep;
import ru.hh.school.adaptation.services.workflow.InterimMeetingResultStep;
import ru.hh.school.adaptation.services.workflow.FinalMeetingStep;
import ru.hh.school.adaptation.services.workflow.FinalMeetingResultStep;
import ru.hh.school.adaptation.services.workflow.QuestionnaireStep;

import javax.inject.Singleton;

@Singleton
public class WorkflowService {

  private AddStep addStep;
  private TaskListStep taskListStep;
  private WelcomeMeetingStep welcomeMeetingStep;
  private InterimMeeteingStep interimMeeteingStep;
  private InterimMeetingResultStep interimMeetingResultStep;
  private FinalMeetingStep finalMeetingStep;
  private FinalMeetingResultStep finalMeetingResultStep;
  private QuestionnaireStep questionnaireStep;

  public WorkflowService(AddStep addStep, TaskListStep taskListStep, WelcomeMeetingStep welcomeMeetingStep,
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
        questionnaireStep.onQuestionnaire(employee);
        break;
    }
  }

}
