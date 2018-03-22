package ru.hh.school.adaptation.entities;

import javax.persistence.*;

@Entity
@Table(name = "workflow_set")
public class WorkflowSet {

  @Id
  @Column(name = "id")
  @GeneratedValue( strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "name")
  private String name;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDescription() {
    return name;
  }

  public void setDescription(String name) {
    this.name = name;
  }
}
