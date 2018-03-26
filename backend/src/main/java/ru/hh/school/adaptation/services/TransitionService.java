package ru.hh.school.adaptation.services;

import org.springframework.transaction.annotation.Transactional;
import ru.hh.school.adaptation.dao.TransitionDao;
import ru.hh.school.adaptation.entities.Transition;
import ru.hh.school.adaptation.entities.WorkflowStepStatus;
import ru.hh.school.adaptation.entities.WorkflowStepType;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class TransitionService {
  private TransitionDao transitionDao;

  public TransitionService(TransitionDao transitionDao){
    this.transitionDao = transitionDao;
  }

  public Transition getCurrentTransitionByEmployeeId(Integer id) {
    return transitionDao.getCurrentTransitionByEmployeeId(id);
  }

  @Transactional
  public void setEmployeeTransition(Integer employeeId, WorkflowStepType typeTo, WorkflowStepStatus setStatusFrom) {
    Transition transitionFrom = transitionDao.getCurrentTransitionByEmployeeId(employeeId);
    transitionFrom.setStepStatus(setStatusFrom);
    transitionDao.update(transitionFrom);

    Transition transitionTo = transitionDao.getTransitionByEmployeeIdwithStepType(employeeId, typeTo);
    transitionTo.setStepStatus(WorkflowStepStatus.COMING);
    transitionDao.update(transitionTo);
  }

  public List<Transition> getAllTransitionByEmployeeId(Integer employeeId) {
    List<Transition> transitionList = transitionDao.getAllTransitionByEmployeeId(employeeId);
    return transitionList;
  }

}
