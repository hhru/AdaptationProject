package ru.hh.school.adaptation.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import ru.hh.school.adaptation.entities.Transition;
import ru.hh.school.adaptation.entities.WorkflowStepStatus;
import ru.hh.school.adaptation.entities.WorkflowStepType;

import java.util.List;

import javax.inject.Inject;

public class TransitionDao {
  private final SessionFactory sessionFactory;

  @Inject
  public TransitionDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
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
  public Transition getCurrentTransitionByEmployeeId(Integer employeeId) {
    Session session = sessionFactory.getCurrentSession();
    Transition transition = (Transition) session.createQuery("from Transition T "
                    + "where T.employee.id=:employeeId and T.stepStatus=:status "
                    + "order by T.id desc")
                    .setParameter("employeeId", employeeId)
                    .setParameter("status", WorkflowStepStatus.COMING)
                    .uniqueResult();
    return transition;
  }

  @Transactional(readOnly = true)
  public Transition getTransitionByEmployeeIdwithStepType(Integer employeeId, WorkflowStepType stepType) {
    Session session = sessionFactory.getCurrentSession();
    Transition transition = (Transition) session.createQuery("from Transition T "
            + "where T.employee.id=:employeeId and T.step=:stepType "
            + "order by T.id desc")
            .setParameter("employeeId", employeeId)
            .setParameter("stepType", stepType)
            .uniqueResult();
    return transition;
  }

  @Transactional
  public void update(Transition transition) {
    sessionFactory.getCurrentSession().update(transition);
  }
}
