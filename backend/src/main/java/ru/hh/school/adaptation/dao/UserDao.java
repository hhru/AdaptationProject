package ru.hh.school.adaptation.dao;

import org.hibernate.SessionFactory;
import ru.hh.school.adaptation.entities.User;

import javax.inject.Inject;

public class UserDao {
  private final SessionFactory sessionFactory;

  @Inject
  public UserDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public User getRecordById(Integer id) {
    return sessionFactory.getCurrentSession().get(User.class, id);
  }
}
