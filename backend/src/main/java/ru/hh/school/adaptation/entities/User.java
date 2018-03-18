package ru.hh.school.adaptation.entities;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "hhid", nullable = false)
  private String hhid;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "middle_name")
  private String middleName;


  public User() {

  }

  public User(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public String getHhid() {
    return hhid;
  }

  public void setHhid(String hhid) {
    this.hhid = hhid;
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
}
