package ru.hh.school.adaptation.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "scheduled_mail")
public class ScheduledMail {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "trigger_date")
  @Temporal(TemporalType.TIMESTAMP)
  private Date triggerDate;

  @Column(name = "employee_id")
  private Integer employeeId;

  @Column(name = "mail_subject")
  private String subject;

  @Column(name = "mail_text")
  private String text;

  @Enumerated(EnumType.STRING)
  @Column(name = "mail_type")
  private ScheduledMailType type;

  @Column(name = "recipients")
  private String recipients;

  public Integer getId() {
    return id;
  }

  public Date getTriggerDate() {
    return triggerDate;
  }

  public void setTriggerDate(Date triggerDate) {
    this.triggerDate = triggerDate;
  }

  public Integer getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(Integer employeeId) {
    this.employeeId = employeeId;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public ScheduledMailType getType() {
    return type;
  }

  public void setType(ScheduledMailType type) {
    this.type = type;
  }

  public String getRecipients() {
    return recipients;
  }

  public void setRecipients(String recipients) {
    this.recipients = recipients;
  }

}
