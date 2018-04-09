package ru.hh.school.adaptation.dao;

import org.hibernate.SessionFactory;

import ru.hh.school.adaptation.entities.Transition;
import ru.hh.school.adaptation.entities.WorkflowStepStatus;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.NonUniqueResultException;

public class TransitionDao {
  private final SessionFactory sessionFactory;

  @Inject
  public TransitionDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public List<Transition> getAllTransitionByEmployeeId(Integer employeeId) {
    return sessionFactory.getCurrentSession().createQuery("from Transition T "
                    + "where T.employee.id=:employeeId order by T.stepType", Transition.class)
                    .setParameter("employeeId", employeeId)
                    .list();
  }

  public Transition getCurrentTransitionByEmployeeId(Integer employeeId) {
    List<Transition> transition = sessionFactory.getCurrentSession().createQuery("from Transition T "
                    + "where T.employee.id=:employeeId and T.stepStatus=:status", Transition.class)
                    .setParameter("employeeId", employeeId)
                    .setParameter("status", WorkflowStepStatus.CURRENT)
                    .list();

    if (transition.isEmpty()) {
      return null;
    }
    else if (transition.size() == 1) {
      return transition.get(0);
    }
    throw new NonUniqueResultException();
  }

  public void update(Transition transition) {
    sessionFactory.getCurrentSession().update(transition);
  }

  public void save(Transition transition) {
    sessionFactory.getCurrentSession().save(transition);
  }

  public Transition getRecordById(Integer id) {
    return sessionFactory.getCurrentSession().get(Transition.class, id);
  }
}
