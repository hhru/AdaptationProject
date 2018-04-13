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

  @Transactional(readOnly = true)
  public MailTemplate getRecordByName(String name) {
    return sessionFactory.getCurrentSession()
            .createQuery("from MailTemplate where name=:name", MailTemplate.class)
            .setParameter("name", name)
            .uniqueResult();
  }

  @Transactional
  public MailTemplate createRecord(String nameTemplate, String htmlTemplate, String title) {
    MailTemplate template = new MailTemplate(nameTemplate, htmlTemplate, title);
    sessionFactory.getCurrentSession().save(template);
    return template;
  }
}
