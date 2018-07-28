package ru.hh.school.adaptation.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.hh.school.adaptation.entities.User;

import javax.inject.Inject;
import java.util.List;

public class UserDao {
  private final SessionFactory sessionFactory;

  @Inject
  public UserDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public User getRecordById(Integer id) {
    return sessionFactory.getCurrentSession().get(User.class, id);
  }

  public User getRecordByAccessRuleId(Integer accessRuleId) {
    return sessionFactory.getCurrentSession()
            .createQuery("from User where access_rule_id=:accessRuleId", User.class)
            .setParameter("accessRuleId", accessRuleId)
            .uniqueResult();
  }

  public List<User> getAllRecords() {
    Session session = sessionFactory.getCurrentSession();
    return session.createQuery("from User", User.class).list();
  }

  public void save(User user) {
    sessionFactory.getCurrentSession().persist(user);
  }

  public void update(User user) {
    sessionFactory.getCurrentSession().update(user);
  }
}
