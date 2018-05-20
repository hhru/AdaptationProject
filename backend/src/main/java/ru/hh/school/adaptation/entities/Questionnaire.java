package ru.hh.school.adaptation.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "questionnaire")
public class Questionnaire {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "employee_id")
  private Employee employee;

  @Column(name = "key")
  private String key;

  @OneToMany(mappedBy = "questionnaire")
  @OrderBy("quest_number")
  private List<QuestionnaireAnswer> answers;

  @Column(name = "is_complete")
  private Boolean isComplete;

  public Integer getId() {
    return id;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public List<QuestionnaireAnswer> getAnswers() {
    return answers;
  }

  public void setAnswers(List<QuestionnaireAnswer> answers) {
    this.answers = answers;
  }

  public Boolean getIsComplete() {
    return isComplete;
  }

  public void setIsComplete(Boolean isComplete) {
    this.isComplete = isComplete;
  }
}
