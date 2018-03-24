package ru.hh.school.adaptation.dao;

import org.hibernate.SessionFactory;

import ru.hh.school.adaptation.entities.Transition;

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

}
