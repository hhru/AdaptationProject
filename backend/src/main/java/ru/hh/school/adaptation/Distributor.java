package ru.hh.school.adaptation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "distributors")
public class Distributor {
  @Id
  @GeneratedValue
  private Integer did;

  @Column(name = "name")
  private String name;

  public Distributor() {
  }

  public Distributor(String name) {
    this.name = name;
  }

  public Integer getDid() {
    return did;
  }

  public void setDid(Integer did) {
    this.did = did;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
