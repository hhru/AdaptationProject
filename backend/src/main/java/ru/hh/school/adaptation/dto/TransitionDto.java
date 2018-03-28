package ru.hh.school.adaptation.dto;

import ru.hh.school.adaptation.entities.Transition;
import ru.hh.school.adaptation.entities.WorkflowStepStatus;

import java.util.Date;

public class TransitionDto {

  public Integer id;
  public Integer employee;
  public Integer workflowStep;
  public Integer nextId;
  public WorkflowStepStatus stepStatus;
  public Date deadlineTimestamp;
  public String comment;

  public TransitionDto() {

  }

  public TransitionDto(Transition transition) {
    id = transition.getId();
    employee = transition.getEmployee().getId();
    workflowStep = transition.getWorkflowStep().getId();
    nextId = transition.getNext() == null?null:transition.getNext().getId();
    stepStatus = transition.getStepStatus();
    deadlineTimestamp = transition.getDeadlineTimestamp();
    comment = transition.getComment();
  }

}
