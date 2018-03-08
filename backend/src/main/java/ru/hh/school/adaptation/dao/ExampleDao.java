package ru.hh.school.adaptation.dao;

import javax.inject.Inject;
import org.hibernate.SessionFactory;
import ru.hh.school.adaptation.entities.Example;

public class ExampleDao {
  private final SessionFactory sessionFactory;

  @Inject
  public ExampleDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public Example getRecordById(Integer id) {
    return sessionFactory.getCurrentSession().get(Example.class, id);
  }
}
