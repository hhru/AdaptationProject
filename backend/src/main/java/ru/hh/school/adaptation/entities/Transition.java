package ru.hh.school.adaptation.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "transition")
public class Transition {

  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "workflow_id")
  private Workflow workflow;

  @ManyToOne(cascade = {CascadeType.ALL})
  @JoinColumn(name = "workflow_step_id")
  private WorkflowStep workflowStep;

  @ManyToOne(cascade = {CascadeType.ALL})
  @JoinColumn(name = "workflow_step_next_id")
  private WorkflowStep workflowStepNext;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Workflow getWorkflow() {
    return workflow;
  }

  public void setWorkflow(Workflow workflow) {
    this.workflow = workflow;
  }

  public WorkflowStep getWorkflowStep() {
    return workflowStep;
  }

  public void setWorkflowStep(WorkflowStep workflowStep) {
    this.workflowStep = workflowStep;
  }

  public WorkflowStep getWorkflowStepNext() {
    return workflowStepNext;
  }

  public void setWorkflowStepNext(WorkflowStep workflowStepNext) {
    this.workflowStepNext = workflowStepNext;
  }
}
