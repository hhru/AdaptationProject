package ru.hh.school.adaptation.services;

import org.springframework.transaction.annotation.Transactional;
import ru.hh.school.adaptation.dao.TransitionDao;
import ru.hh.school.adaptation.entities.Transition;
import ru.hh.school.adaptation.entities.WorkflowStepStatus;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class TransitionService {
  private TransitionDao transitionDao;

  public TransitionService(TransitionDao transitionDao){
    this.transitionDao = transitionDao;
  }

  @Transactional(readOnly = true)
  public Transition getCurrentTransitionByEmployeeId(Integer id) {
    return transitionDao.getCurrentTransitionByEmployeeId(id);
  }

  @Transactional
  public void setEmployeeNextTransition(Integer employeeId) {
    Transition transitionFrom = transitionDao.getCurrentTransitionByEmployeeId(employeeId);
    Transition transitionTo = transitionDao.getNextTransitionByEmployeeId(employeeId);

    transitionFrom.setStepStatus(WorkflowStepStatus.DONE);
    transitionTo.setStepStatus(WorkflowStepStatus.CURRENT);

    transitionDao.update(transitionFrom);
    transitionDao.update(transitionTo);
  }

  @Transactional(readOnly = true)
  public List<Transition> getAllTransitionByEmployeeId(Integer employeeId) {
    return transitionDao.getAllTransitionByEmployeeId(employeeId);
  }

}
