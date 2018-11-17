package ru.hh.school.adaptation.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "task")
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "task_form_id")
  private TaskForm taskForm;

  @Column(name = "text")
  private String text;

  @Column(name = "deadline")
  private String deadline;

  @Column(name = "is_weeks")
  private Boolean isWeeks;

  @Column(name = "resources")
  private String resources;

  @Column(name = "comment")
  private String comment;

  @Column(name = "is_deleted")
  private Boolean isDeleted;

  public Integer getId() {
    return id;
  }

  public TaskForm getTaskForm() {
    return taskForm;
  }

  public void setTaskForm(TaskForm taskForm) {
    this.taskForm = taskForm;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getDeadline() {
    return deadline;
  }

  public void setDeadline(String deadline) {
    this.deadline = deadline;
  }

  public Boolean getIsWeeks() {
    return isWeeks;
  }

  public void setIsWeeks(Boolean isWeeks) {
    this.isWeeks = isWeeks;
  }

  public String getResources() {
    return resources;
  }

  public void setResources(String resources) {
    this.resources = resources;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public Boolean getDeleted() {
    return isDeleted;
  }

  public void setDeleted(Boolean deleted) {
    isDeleted = deleted;
  }

}
