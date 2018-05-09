package ru.hh.school.adaptation.dto;

import ru.hh.school.adaptation.entities.QuestionnaireAnswer;

public class QuestionnaireAnswerDto {
  public Integer questNumber;
  public Integer answerNumber;
  public String answerText;

  public QuestionnaireAnswerDto(){

  }

  public QuestionnaireAnswerDto(QuestionnaireAnswer answer){
    questNumber = answer.getQuestNumber();
    answerNumber = answer.getAnswerNumber();
    answerText = answer.getAnswerText();
  }

}
