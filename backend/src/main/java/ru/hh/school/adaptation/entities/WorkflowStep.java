package ru.hh.school.adaptation.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "workflow_step")
public class WorkflowStep {

  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY)
  private Integer id;

  @Enumerated(EnumType.STRING)
  @Column(name = "name")
  private WorkflowStepType name;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public WorkflowStepType getName() {
    return name;
  }

  public void setName(WorkflowStepType workflowStepType) {
    this.name = workflowStepType;
  }
}
