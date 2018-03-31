package ru.hh.school.adaptation.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transition")
public class Transition {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "next_id")
  private Transition next;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "employee_id")
  private Employee employee;

  @Enumerated(EnumType.STRING)
  @Column(name = "step_type")
  private WorkflowStepType stepType;

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

  public Transition getNext() {
    return next;
  }

  public void setNext(Transition next) {
    this.next = next;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public WorkflowStepType getStepType() {
    return stepType;
  }

  public void setStepType(WorkflowStepType stepType) {
    this.stepType = stepType;
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
