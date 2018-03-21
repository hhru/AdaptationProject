package ru.hh.school.adaptation.dao;

import org.hibernate.SessionFactory;

import ru.hh.school.adaptation.entities.Transition;
import ru.hh.school.adaptation.entities.Workflow;

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
  public List<Workflow> getAllRecordsBySetId(Integer workflowSetId) {
    return sessionFactory.getCurrentSession().createQuery(
        "SELECT T.workflow from Transition T "
        + "where T.workflowSet=:workflowSetId order by T.workflow")
        .setInteger("workflowSetId", workflowSetId)
        .list();
  }
}
