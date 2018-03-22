package ru.hh.school.adaptation.dto;

import ru.hh.school.adaptation.entities.WorkflowStep;

public class WorkflowStepDto {

  public Integer id;
  public String name;
  public String description;

  public WorkflowStepDto(WorkflowStep workflowStep) {
    id = workflowStep.getId();
    name = workflowStep.getName();
    description = workflowStep.getDescription();
  }

}
