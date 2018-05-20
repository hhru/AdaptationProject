package ru.hh.school.adaptation.dao;

import org.hibernate.SessionFactory;
import ru.hh.school.adaptation.entities.Questionnaire;

import javax.inject.Inject;

public class QuestionnaireDao {
  private final SessionFactory sessionFactory;

  @Inject
  public QuestionnaireDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public Questionnaire getRecordById(Integer id) {
    return sessionFactory.getCurrentSession().get(Questionnaire.class, id);
  }

  public Questionnaire getRecordByEmployeeId(Integer employeeId) {
    return sessionFactory.getCurrentSession()
        .createQuery("from Questionnaire where Questionnaire.employee.id=:employeeId", Questionnaire.class)
        .setParameter("employeeId", employeeId)
        .uniqueResult();
  }

  public Questionnaire getRecordByKey(String key) {
    return sessionFactory.getCurrentSession()
        .createQuery("from Questionnaire Q where Q.key=:key", Questionnaire.class)
        .setParameter("key", key)
        .uniqueResult();
  }

  public void save(Questionnaire questionnaire) {
    sessionFactory.getCurrentSession().persist(questionnaire);
  }

  public void update(Questionnaire questionnaire) {
    sessionFactory.getCurrentSession().update(questionnaire);
  }

}
