package ru.hh.school.adaptation.dao;

import java.util.List;
import javax.inject.Inject;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.school.adaptation.entities.ScheduledMail;
import static ru.hh.school.adaptation.entities.ScheduledMailType.PROBATION_RESULT;

public class ScheduledMailDao {
  private final SessionFactory sessionFactory;

  @Inject
  public ScheduledMailDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public void save(ScheduledMail scheduledMail) {
    sessionFactory.getCurrentSession().save(scheduledMail);
  }

  @Transactional
  public void delete(ScheduledMail scheduledMail){
    sessionFactory.getCurrentSession().delete(scheduledMail);
  }

  public List<ScheduledMail> getAll() {
    return sessionFactory.getCurrentSession().createQuery("from ScheduledMail", ScheduledMail.class).list();
  }

  public void update(ScheduledMail scheduledMail) {
    sessionFactory.getCurrentSession().update(scheduledMail);
  }

  public ScheduledMail getProbationScheduledMailByEmployeeId(Integer employeeId) {
    return sessionFactory.getCurrentSession()

    .createQuery("from ScheduledMail S where S.employeeId=:employeeId and S.type=:type", ScheduledMail.class)
        .setParameter("employeeId", employeeId)
        .setParameter("type", PROBATION_RESULT)
        .uniqueResult();
  }
}
