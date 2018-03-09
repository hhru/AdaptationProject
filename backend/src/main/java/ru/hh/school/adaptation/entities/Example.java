package ru.hh.school.adaptation.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "example")
public class Example {
  @Id
  private Integer id;

  @Column(name = "name")
  private String name;

  public Integer getId() {
    return id;
  }

  public void setid(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
