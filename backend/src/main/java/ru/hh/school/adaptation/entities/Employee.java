package ru.hh.school.adaptation.entities;

import java.util.Date;

import javax.persistence.CascadeType;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "employee")
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false)
  private Integer id;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "middle_name")
  private String middleName;

  @Column(name = "position", nullable = false)
  private String position;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "mobile_phone")
  private Long mobilePhone;

  @Column(name = "internal_phone")
  private Integer internalPhone;

  @Enumerated(EnumType.STRING)
  @Column(name = "gender")
  private Gender gender;

  @Column(name = "employment_timestamp")
  @Temporal(TemporalType.TIMESTAMP)
  private Date employmentTimestamp;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "workflow_step_id")
  private WorkflowStep workflowStep;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "workflow_id")
  private Workflow workflow;

  @ManyToOne(cascade = {CascadeType.ALL})
  @JoinColumn(name = "mentor_id")
  private User mentor;

  @ManyToOne(cascade = {CascadeType.ALL})
  @JoinColumn(name = "chief_id")
  private User chief;

  public Integer getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getEmploymentTimestamp() {
    return employmentTimestamp;
  }

  public void setEmploymentTimestamp(Date employmentTimestamp) {
    this.employmentTimestamp = employmentTimestamp;
  }

  public WorkflowStep getWorkflowStep() {
    return workflowStep;
  }

  public void setWorkflowStep(WorkflowStep workflowStep) {
    this.workflowStep = workflowStep;
  }

  public Workflow getWorkflow() {
    return workflow;
  }

  public void setWorkflow(Workflow workflow) {
    this.workflow = workflow;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public User getMentor() {
    return mentor;
  }

  public void setMentor(User mentor) {
    this.mentor = mentor;
  }

  public Long getMobilePhone() {
    return mobilePhone;
  }

  public void setMobilePhone(Long mobilePhone) {
    this.mobilePhone = mobilePhone;
  }

  public Integer getInternalPhone() {
    return internalPhone;
  }

  public void setInternalPhone(Integer internalPhone) {
    this.internalPhone = internalPhone;
  }

  public User getChief() {
    return chief;
  }

  public void setChief(User chief) {
    this.chief = chief;
  }
}
