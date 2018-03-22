package ru.hh.school.adaptation.entities;

import javax.persistence.*;

@Entity
@Table(name = "workflow_step")
public class WorkflowStep {

  @Id
  @Column(name = "id")
  private Integer id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "description")
  private String description;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}