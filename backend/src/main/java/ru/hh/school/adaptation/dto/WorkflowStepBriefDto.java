package ru.hh.school.adaptation.dto;

import ru.hh.school.adaptation.entities.Transition;
import ru.hh.school.adaptation.entities.WorkflowStepStatus;
import ru.hh.school.adaptation.entities.WorkflowStepType;

public class WorkflowStepBriefDto {

  public Integer id;

  public WorkflowStepType type;

  public WorkflowStepStatus status;

  public WorkflowStepBriefDto(Transition transition){
    id = transition.getId();
    type = transition.getStepType();
    status = transition.getStepStatus();
  }

}
