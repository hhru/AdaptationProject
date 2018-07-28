package ru.hh.school.adaptation.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.hh.school.adaptation.entities.AccessRule;

import javax.inject.Inject;
import java.util.List;

public class AccessRuleDao {
  private final SessionFactory sessionFactory;

  @Inject
  public AccessRuleDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public AccessRule getByHhId(Integer hhId) {
    return sessionFactory.getCurrentSession().createQuery("from AccessRule WHERE hhid = :hhId", AccessRule.class)
            .setParameter("hhId", hhId)
            .uniqueResult();
  }

  public void save(AccessRule accessRule) {
    sessionFactory.getCurrentSession().save(accessRule);
  }

  public void update(AccessRule accessRule) {
    sessionFactory.getCurrentSession().update(accessRule);
  }

  public List<AccessRule> getAllRecords() {
    Session session = sessionFactory.getCurrentSession();
    return session.createQuery("from AccessRule", AccessRule.class).list();
  }

}
