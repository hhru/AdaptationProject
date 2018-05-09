package ru.hh.school.adaptation.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "questionnaire_answer")
public class QuestionnaireAnswer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "questionnaire_id")
  private Questionnaire questionnaire;

  @Column(name = "quest_number")
  private Integer questNumber;

  @Column(name = "answer_number")
  private Integer answerNumber;

  @Column(name = "answer_text")
  private String answerText;

  public Integer getId() {
    return id;
  }

  public Questionnaire getQuestionnaire() {
    return questionnaire;
  }

  public void setQuestionnaire(Questionnaire questionnaire) {
    this.questionnaire = questionnaire;
  }

  public Integer getQuestNumber() {
    return questNumber;
  }

  public void setQuestNumber(Integer questNumber) {
    this.questNumber = questNumber;
  }

  public Integer getAnswerNumber() {
    return answerNumber;
  }

  public void setAnswerNumber(Integer answerNumber) {
    this.answerNumber = answerNumber;
  }

  public String getAnswerText() {
    return answerText;
  }

  public void setAnswerText(String answerText) {
    this.answerText = answerText;
  }

}
