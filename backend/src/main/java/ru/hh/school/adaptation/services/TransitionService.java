package ru.hh.school.adaptation.services;

import org.springframework.transaction.annotation.Transactional;
import ru.hh.school.adaptation.dao.TransitionDao;
import ru.hh.school.adaptation.entities.Transition;
import ru.hh.school.adaptation.entities.WorkflowStepStatus;

import javax.inject.Singleton;

import java.util.Iterator;
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
    List<Transition> transitionList = transitionDao.getAllTransitionByEmployeeId(employeeId);
    Iterator<Transition> iter = transitionList.iterator();
    Transition transitionCurrent;
    Transition transitionNext;
    do {
      transitionCurrent = iter.next();
    } while (iter.hasNext() && transitionCurrent.getStepStatus()!=WorkflowStepStatus.CURRENT);
    transitionCurrent.setStepStatus(WorkflowStepStatus.DONE);

    do {
      transitionNext = iter.next();
    } while (iter.hasNext() && transitionCurrent.getStepStatus()==WorkflowStepStatus.IGNORE);
    if (transitionNext==null || transitionCurrent.getStepStatus()==WorkflowStepStatus.IGNORE) {
      return;
    }
    transitionNext.setStepStatus(WorkflowStepStatus.CURRENT);

    transitionDao.update(transitionCurrent);
    transitionDao.update(transitionNext);
  }

  @Transactional(readOnly = true)
  public List<Transition> getAllTransitionByEmployeeId(Integer employeeId) {
    return transitionDao.getAllTransitionByEmployeeId(employeeId);
  }

}
