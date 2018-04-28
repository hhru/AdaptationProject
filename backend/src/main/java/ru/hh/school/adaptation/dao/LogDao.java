package ru.hh.school.adaptation.dao;

import org.hibernate.SessionFactory;
import ru.hh.school.adaptation.entities.Log;

import javax.inject.Inject;

public class LogDao {
  private final SessionFactory sessionFactory;

  @Inject
  public LogDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public void save(Log log) {
    sessionFactory.getCurrentSession().save(log);
  }

}
