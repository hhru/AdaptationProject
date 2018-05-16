package ru.hh.school.adaptation.dao;

import org.hibernate.SessionFactory;
import ru.hh.school.adaptation.entities.QuestionnaireAnswer;

import javax.inject.Inject;

public class QuestionnaireAnswerDao {

  private final SessionFactory sessionFactory;

  @Inject
  public QuestionnaireAnswerDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public QuestionnaireAnswer getRecordById(Integer id) {
    return sessionFactory.getCurrentSession().get(QuestionnaireAnswer.class, id);
  }

  public void save(QuestionnaireAnswer answer) {
    sessionFactory.getCurrentSession().save(answer);
  }

  public void update(QuestionnaireAnswer answer) {
    sessionFactory.getCurrentSession().update(answer);
  }

}
