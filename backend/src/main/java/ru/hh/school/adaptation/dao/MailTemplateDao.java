package ru.hh.school.adaptation.dao;

import javax.inject.Inject;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import ru.hh.school.adaptation.entities.MailTemplate;

public class MailTemplateDao {
  private final SessionFactory sessionFactory;

  @Inject
  public MailTemplateDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public MailTemplate getRecordById(Integer id) {
    return sessionFactory.getCurrentSession().get(MailTemplate.class, id);
  }

  public MailTemplate getRecordByName(String name) {
    return (MailTemplate) sessionFactory.getCurrentSession()
            .createQuery("from MailTemplate where name=:name")
            .setParameter("name", name)
            .uniqueResult();
  }
  
  @Transactional
  public MailTemplate createRecord(String nameTemplate, String htmlTemplate) {
    MailTemplate template = new MailTemplate(nameTemplate, htmlTemplate);
    sessionFactory.getCurrentSession().save(template);
    return template;
  }
}
