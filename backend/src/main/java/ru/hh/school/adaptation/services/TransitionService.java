package ru.hh.school.adaptation.services;

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

  public Transition getTransitionByEmployeeId(Integer id, WorkflowStepStatus status) {
    return transitionDao.getTransitionByEmployeeId(id, status);
  }

  public void setEmployeeTransition(Integer employeeId, WorkflowStepStatus statusTo, WorkflowStepStatus statusFrom) {
    //tekushi nado sdelat DONE
    //to nado sdelat coming
    transitionDao.setTransitionByEmployeeId(employeeId, statusTo);
  }

  public List<Transition> getAllTransitionByEmployeeId(Integer employeeId) {
    List<Transition> transitionList = transitionDao.getAllTransitionByEmployeeId(employeeId);
    return transitionList;
  }

}
