package ru.hh.school.adaptation.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;


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

  @OneToMany(mappedBy = "author")
  @OrderBy("id")
  private List<Comment> comments;

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

  public List<Comment> getComments() {
    return comments;
  }

  public void setComments(List<Comment> comments) {
    this.comments = comments;
  }

}
