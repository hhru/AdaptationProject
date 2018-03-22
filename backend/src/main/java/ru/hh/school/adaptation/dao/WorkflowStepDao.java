package ru.hh.school.adaptation.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import ru.hh.school.adaptation.entities.WorkflowStep;

import java.util.List;

import javax.inject.Inject;

public class WorkflowStepDao {
  private final SessionFactory sessionFactory;

  @Inject
  public WorkflowStepDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public WorkflowStep getRecordById(Integer id) {
    return sessionFactory.getCurrentSession().get(WorkflowStep.class, id);
  }

  public List<WorkflowStep> getAllRecords() {
    Session session = sessionFactory.getCurrentSession();
    return session.createQuery("from WorkflowStep", WorkflowStep.class).list();
  }
}
