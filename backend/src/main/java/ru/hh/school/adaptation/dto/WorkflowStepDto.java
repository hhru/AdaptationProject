package ru.hh.school.adaptation.dto;

import ru.hh.school.adaptation.entities.Workflow;

public class WorkflowDto {

  public Integer id;
  public String name;
  public String description;

  public WorkflowDto(Workflow workflow) {
    id = workflow.getId();
    name = workflow.getName();
    description = workflow.getDescription();
  }

}
