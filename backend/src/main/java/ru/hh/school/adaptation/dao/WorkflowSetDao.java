package ru.hh.school.adaptation.dao;

import org.hibernate.SessionFactory;
import ru.hh.school.adaptation.entities.WorkflowSet;

import javax.inject.Inject;

public class WorkflowSetDao {
  private final SessionFactory sessionFactory;

  @Inject
  public WorkflowSetDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public WorkflowSet getRecordById(Integer id) {
    return sessionFactory.getCurrentSession().get(WorkflowSet.class, id);
  }
}
