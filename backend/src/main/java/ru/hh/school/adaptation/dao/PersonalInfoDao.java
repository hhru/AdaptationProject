package ru.hh.school.adaptation.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.hh.school.adaptation.entities.PersonalInfo;

import javax.inject.Inject;
import java.util.List;

public class PersonalInfoDao {
  private final SessionFactory sessionFactory;

  @Inject
  public PersonalInfoDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public PersonalInfo getRecordById(Integer id) {
    return sessionFactory.getCurrentSession().get(PersonalInfo.class, id);
  }

  public List<PersonalInfo> getAllRecords() {
    Session session = sessionFactory.getCurrentSession();
    return session.createQuery("from PersonalInfo", PersonalInfo.class).list();
  }

  public void save(PersonalInfo personalInfo) {
    sessionFactory.getCurrentSession().persist(personalInfo);
  }

  public void update(PersonalInfo personalInfo) {
    sessionFactory.getCurrentSession().update(personalInfo);
  }
}
