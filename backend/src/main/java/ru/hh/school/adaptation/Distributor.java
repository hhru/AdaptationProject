package ru.hh.school.adaptation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "distributors")
public class Distributor {
  @Id
  private Integer did;

  @Column(name = "name")
  private String name;

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
