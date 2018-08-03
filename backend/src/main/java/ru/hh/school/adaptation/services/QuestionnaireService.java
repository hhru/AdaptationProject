package ru.hh.school.adaptation.services;

import org.springframework.transaction.annotation.Transactional;
import ru.hh.nab.core.util.FileSettings;
import ru.hh.school.adaptation.dao.EmployeeDao;
import ru.hh.school.adaptation.dao.QuestionnaireAnswerDao;
import ru.hh.school.adaptation.dao.QuestionnaireDao;
import ru.hh.school.adaptation.dto.QuestionnaireAnswerDto;
import ru.hh.school.adaptation.dto.QuestionnaireDto;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.entities.Questionnaire;
import ru.hh.school.adaptation.entities.QuestionnaireAnswer;
import ru.hh.school.adaptation.exceptions.EntityNotFoundException;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Singleton
public class QuestionnaireService {

  private String adaptationHost;

  private QuestionnaireDao questionnaireDao;
  private QuestionnaireAnswerDao questionnaireAnswerDao;
  private EmployeeDao employeeDao;
  private MailService mailService;

  public QuestionnaireService(FileSettings fileSettings, QuestionnaireDao questionnaireDao, QuestionnaireAnswerDao questionnaireAnswerDao,
                              EmployeeDao employeeDao, MailService mailService) {
    this.questionnaireDao = questionnaireDao;
    this.questionnaireAnswerDao = questionnaireAnswerDao;
    this.employeeDao = employeeDao;
    this.mailService = mailService;

    adaptationHost = fileSettings.getProperties().getProperty("adaptation.host");
  }

  @Transactional
  public Questionnaire createQuestionnaire(Employee employee) {
    Questionnaire questionnaire = employee.getQuestionnaire();
    if (questionnaire == null) {
      questionnaire = new Questionnaire();
      questionnaire.setEmployee(employee);
      questionnaire.setKey(UUID.randomUUID().toString().replace("-", ""));
      questionnaire.setIsComplete(false);
      questionnaireDao.save(questionnaire);
    }
    return questionnaire;
  }

  @Transactional(readOnly = true)
  public Questionnaire getQuestionnaireByKey(String key) {
    Questionnaire questionnaire = questionnaireDao.getRecordByKey(key);
    if (questionnaire == null) {
      throw new EntityNotFoundException(String.format("Questionnaire with key=%s is not exist", key));
    }
    return questionnaire;
  }

  @Transactional(readOnly = true)
  public QuestionnaireDto getQuestionnairesDtoByKey(String key) {
    return new QuestionnaireDto(getQuestionnaireByKey(key));
  }

  @Transactional
  public void submitQuestionnaire(QuestionnaireDto questionnaireDto){
    Questionnaire questionnaire = questionnaireDao.getRecordByKey(questionnaireDto.key);
    if (questionnaire.getIsComplete()) {
      return;
    }
    List<QuestionnaireAnswer> answers = new LinkedList<>();
    for(QuestionnaireAnswerDto answerDto : questionnaireDto.answers){
      if (answerDto != null){
        QuestionnaireAnswer answer = new QuestionnaireAnswer();

        answer.setQuestNumber(answerDto.questNumber);
        answer.setAnswerNumber(answerDto.answerNumber);
        answer.setAnswerText(answerDto.answerText);
        answer.setQuestionnaire(questionnaire);

        questionnaireAnswerDao.save(answer);
        answers.add(answer);
      }
    }
    questionnaire.setAnswers(answers);
    questionnaire.setIsComplete(true);
    questionnaireDao.update(questionnaire);

    notifyHr(questionnaire.getEmployee());
  }

  private void notifyHr(Employee employee){
    Map<String, String> params = new HashMap<>();
    String fio = String.format("%s %s %s",
        employee.getSelf().getLastName(),
        employee.getSelf().getFirstName(),
        employee.getSelf().getMiddleName());
    String taskUrl = String.format("https://" + adaptationHost + "/employee/%d/questionnaire", employee.getId());
    params.put("{{userName}}", fio);
    params.put("{{questionnaireUrl}}", taskUrl);
    mailService.sendMail(employee.getHr().getSelf().getEmail(), "hr_questionnaire_notify", params);
  }

  @Transactional(readOnly = true)
  public Questionnaire getQuestionnaireByEmployee(Integer employeeId) {
    return employeeDao.getRecordById(employeeId).getQuestionnaire();
  }

  @Transactional(readOnly = true)
  public QuestionnaireDto getQuestionnaireDtoByEmployee(Integer employeeId) {
    return new QuestionnaireDto(getQuestionnaireByEmployee(employeeId));
  }

}
