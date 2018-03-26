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

  public User getRecordByHhid(Integer hhid) {
    return sessionFactory.getCurrentSession()
            .createQuery("from User where hhid=:hhid", User.class)
            .setParameter("hhid", hhid)
            .uniqueResult();
  }

  public void save(User user) {
    sessionFactory.getCurrentSession().persist(user);
  }

  public void update(User user) {
    sessionFactory.getCurrentSession().update(user);
  }
}
