package ru.hh.school.adaptation.dao;

import org.hibernate.SessionFactory;

import ru.hh.school.adaptation.entities.Transition;
import ru.hh.school.adaptation.entities.WorkflowStep;

import java.util.List;

import javax.inject.Inject;

public class TransitionDao {
  private final SessionFactory sessionFactory;

  @Inject
  public TransitionDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public Transition getRecordById(Integer id) {
    return sessionFactory.getCurrentSession().get(Transition.class, id);
  }

  @SuppressWarnings({ "unchecked", "deprecation" })
  public List<WorkflowStep> getAllRecords(Integer workflowId) {
    return sessionFactory.getCurrentSession().createQuery(
        "SELECT T.workflowStep from Transition T "
        + "where T.workflow=:workflowId order by T.workflowStep")
        .setInteger("workflowId", workflowId)
        .list();
  }
}
