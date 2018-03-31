package ru.hh.school.adaptation.dao;

import org.hibernate.SessionFactory;
import ru.hh.school.adaptation.entities.PersonalInfo;

import javax.inject.Inject;

public class PersonalInfoDao {
  private final SessionFactory sessionFactory;

  @Inject
  public PersonalInfoDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public void save(PersonalInfo personalInfo) {
    sessionFactory.getCurrentSession().persist(personalInfo);
  }

  public void update(PersonalInfo personalInfo) {
    sessionFactory.getCurrentSession().update(personalInfo);
  }
}
