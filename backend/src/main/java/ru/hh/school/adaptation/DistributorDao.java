package ru.hh.school.adaptation;

import javax.inject.Inject;
import org.hibernate.SessionFactory;

public class DistributorDao {
  private final SessionFactory sessionFactory;

  @Inject
  public DistributorDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public Distributor getDistributorById(Integer id) {
    return sessionFactory.getCurrentSession().get(Distributor.class, id);
  }
}
