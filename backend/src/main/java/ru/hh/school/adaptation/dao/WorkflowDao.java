package ru.hh.school.adaptation.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import ru.hh.school.adaptation.entities.Workflow;

import java.util.List;

import javax.inject.Inject;

public class WorkflowDao {
  private final SessionFactory sessionFactory;

  @Inject
  public WorkflowDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public Workflow getRecordById(Integer id) {
    return sessionFactory.getCurrentSession().get(Workflow.class, id);
  }

  public List<Workflow> getAllRecords() {
    Session session = sessionFactory.getCurrentSession();
    return session.createQuery("from Workflow", Workflow.class).list();
  }
}
