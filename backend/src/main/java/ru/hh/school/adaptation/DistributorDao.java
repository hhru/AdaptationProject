package ru.hh.school.adaptation;

import javax.inject.Inject;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

public class DistributorDao {
  private final SessionFactory sessionFactory;

  @Inject
  public DistributorDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Transactional
  public Distributor getDistributorById(Integer id) {
    return sessionFactory.getCurrentSession().get(Distributor.class, id);
  }

  @Transactional
  public Distributor addDistributor(String name) {
    Distributor distributor = new Distributor(name);

    sessionFactory.getCurrentSession().save(distributor);

    return distributor;
  }
}
