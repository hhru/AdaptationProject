package ru.hh.school.adaptation.dto;

import ru.hh.school.adaptation.entities.Questionnaire;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionnaireDto {

  public String key;
  public Boolean isComplete;
  public Boolean isManager;
  public List<QuestionnaireAnswerDto> answers;

  public QuestionnaireDto(){

  }

  public QuestionnaireDto(Questionnaire questionnaire){
    key = questionnaire.getKey();
    isComplete = questionnaire.getIsComplete();
    isManager = false;
    if (questionnaire.getAnswers() != null){
      answers = questionnaire.getAnswers().stream().map(QuestionnaireAnswerDto::new).collect(Collectors.toList());
    }
  }

}
