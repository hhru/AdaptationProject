package ru.hh.school.adaptation.entities;

import javax.persistence.*;

@Entity
@Table(name = "transition")
public class Transition {

  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "workflow_set_id")
  private WorkflowSet workflowSet;

  @ManyToOne(cascade = {CascadeType.ALL})
  @JoinColumn(name = "workflow_id")
  private Workflow workflow;

  @ManyToOne(cascade = {CascadeType.ALL})
  @JoinColumn(name = "workflow_next_id")
  private Workflow workflowNext;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public WorkflowSet getWorkflowSet() {
    return workflowSet;
  }

  public void setWorkflowSet(WorkflowSet workflowSet) {
    this.workflowSet = workflowSet;
  }

  public Workflow getWorkflow() {
    return workflow;
  }

  public void setWorkflow(Workflow workflow) {
    this.workflow = workflow;
  }

  public Workflow getWorkflowNext() {
    return workflowNext;
  }

  public void setWorkflowNext(Workflow workflowNext) {
    this.workflowNext = workflowNext;
  }
}
