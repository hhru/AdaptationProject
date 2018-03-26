package ru.hh.school.adaptation.dao;

import org.hibernate.Session;
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

  public Transition getRecordById(Integer id) {
    return sessionFactory.getCurrentSession().get(Transition.class, id);
  }

  @Transactional(readOnly = true)
  public List<Transition> getAllTransitionByEmployeeId(Integer employeeId) {
    Session session = sessionFactory.getCurrentSession();
	List<Transition> transitionList = session.createQuery("from Transition T "
                    + "where T.employee.id=:employeeId order by T.id", Transition.class)
                    .setParameter("employeeId", employeeId)
                    .list();
    return transitionList;
  }

  @Transactional(readOnly = true)
  public Transition getTransitionByEmployeeId(Integer employeeId, WorkflowStepStatus status) {
    Session session = sessionFactory.getCurrentSession();
    Transition transition = (Transition) session.createQuery("from Transition T "
                    + "where T.employee.id=:employeeId and T.stepStatus=:status "
                    + "order by T.id desc")
                    .setParameter("employeeId", employeeId)
                    .setParameter("status", status)
                    .setMaxResults(1).uniqueResult();
    return transition;
  }

  @Transactional(readOnly = true)
  public void setTransitionByEmployeeId(Integer employeeId, WorkflowStepStatus status) {
    //q
  }
}
