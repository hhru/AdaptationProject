package ru.hh.school.adaptation.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "access_rule")
public class AccessRule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "hhid", nullable = false)
  private Integer hhid;

  @Enumerated(EnumType.STRING)
  @Column(name = "access_type")
  private AccessType accessType;

  @OneToOne(mappedBy = "accessRule")
  private User user;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getHhId() {
    return hhid;
  }

  public void setHhId(Integer hhid) {
    this.hhid = hhid;
  }

  public AccessType getAccessType() {
    return accessType;
  }

  public void setAccessType(AccessType accessType) {
    this.accessType = accessType;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
