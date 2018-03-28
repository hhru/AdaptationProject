package ru.hh.school.adaptation.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "transition")
public class Transition {

  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "employee_id")
  private Employee employee;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "workflow_step_id")
  private WorkflowStep workflowStep;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "next_id")
  private Transition nextId;

  @Enumerated(EnumType.STRING)
  @Column(name = "step_status")
  private WorkflowStepStatus stepStatus;

  @Column(name = "deadline_timestamp")
  @Temporal(TemporalType.TIMESTAMP)
  private Date deadlineTimestamp;

  @Column(name = "comment")
  private String comment;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public WorkflowStep getWorkflowStep() {
    return workflowStep;
  }

  public void setWorkflowStep(WorkflowStep workflowStep) {
    this.workflowStep = workflowStep;
  }

  public Transition getNextId() {
    return nextId;
  }

  public void setNextId(Transition nextId) {
    this.nextId = nextId;
  }

  public WorkflowStepStatus getStepStatus() {
    return stepStatus;
  }

  public void setStepStatus(WorkflowStepStatus stepStatus) {
    this.stepStatus = stepStatus;
  }

  public Date getDeadlineTimestamp() {
    return deadlineTimestamp;
  }

  public void setDeadlineTimestamp(Date deadlineTimestamp) {
    this.deadlineTimestamp = deadlineTimestamp;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }
}
