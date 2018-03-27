package ru.hh.school.adaptation.dao;

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

  public List<WorkflowStep> getAllWorkflowSteps() {
	return sessionFactory.getCurrentSession().createQuery("from WorkflowStep", WorkflowStep.class).list();
  }
}
