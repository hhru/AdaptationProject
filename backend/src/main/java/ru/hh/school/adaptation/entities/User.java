package ru.hh.school.adaptation.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;


@Entity
@Table(name = "`user`")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "hhid", nullable = false)
  private Integer hhid;

  @ManyToOne(cascade = {CascadeType.ALL})
  @JoinColumn(name = "self_id")
  private PersonalInfo self;

  public Integer getId() {
    return id;
  }

  public Integer getHhid() {
    return hhid;
  }

  public void setHhid(Integer hhid) {
    this.hhid = hhid;
  }

  public PersonalInfo getSelf() {
    return self;
  }

  public void setSelf(PersonalInfo self) {
    this.self = self;
  }
}
