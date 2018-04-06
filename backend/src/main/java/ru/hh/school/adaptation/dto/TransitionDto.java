package ru.hh.school.adaptation.dto;

import ru.hh.school.adaptation.entities.Transition;
import ru.hh.school.adaptation.entities.WorkflowStepStatus;
import ru.hh.school.adaptation.entities.WorkflowStepType;

import java.util.Date;

public class TransitionDto {

  public Integer id;
  public Integer nextId;
  public Integer employee;
  public WorkflowStepType stepType;
  public WorkflowStepStatus stepStatus;
  public Date deadlineTimestamp;
  public String comment;

  public TransitionDto() {
  }

  public TransitionDto(Transition transition) {
    id = transition.getId();
    employee = transition.getEmployee().getId();
    stepType = transition.getStepType();
    nextId = transition.getNext() == null?null:transition.getNext().getId();
    stepStatus = transition.getStepStatus();
    deadlineTimestamp = transition.getDeadlineTimestamp();
    comment = transition.getComment();
  }

}
