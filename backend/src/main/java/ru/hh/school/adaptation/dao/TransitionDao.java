package ru.hh.school.adaptation.dao;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import ru.hh.school.adaptation.entities.Transition;
import ru.hh.school.adaptation.entities.WorkflowStepStatus;

import java.util.List;

import javax.inject.Inject;

public class TransitionDao {
  private final SessionFactory sessionFactory;

  @Inject
  public TransitionDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public List<Transition> getAllTransitionByEmployeeId(Integer employeeId) {
	return sessionFactory.getCurrentSession().createQuery("from Transition T "
                    + "where T.employee.id=:employeeId order by T.id", Transition.class)
                    .setParameter("employeeId", employeeId)
                    .list();
  }

  public Transition getCurrentTransitionByEmployeeId(Integer employeeId) {
    return sessionFactory.getCurrentSession().createQuery("from Transition T "
                    + "where T.employee.id=:employeeId and T.stepStatus=:status", Transition.class)
                    .setParameter("employeeId", employeeId)
                    .setParameter("status", WorkflowStepStatus.CURRENT)
                    .uniqueResult();
  }

  public Transition getNextTransitionByEmployeeId(Integer employeeId) {
    return sessionFactory.getCurrentSession().createQuery("from Transition T "
            + "where T.id>(select T.id from T where T.employee.id=:employeeId and T.stepStatus=:current) and "
            + "T.stepStatus!=:ignore order by T.id", Transition.class)
            .setParameter("employeeId", employeeId)
            .setParameter("current", WorkflowStepStatus.CURRENT)
            .setParameter("ignore", WorkflowStepStatus.IGNORE)
            .setMaxResults(1)
            .uniqueResult();
  }

  @Transactional
  public void update(Transition transition) {
    sessionFactory.getCurrentSession().update(transition);
  }
}
