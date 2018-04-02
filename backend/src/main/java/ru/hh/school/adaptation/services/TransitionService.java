package ru.hh.school.adaptation.services;

import org.springframework.transaction.annotation.Transactional;
import ru.hh.school.adaptation.dao.TransitionDao;
import ru.hh.school.adaptation.dto.TransitionDto;
import ru.hh.school.adaptation.entities.Transition;
import ru.hh.school.adaptation.entities.WorkflowStepStatus;

import javax.inject.Singleton;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class TransitionService {
  private TransitionDao transitionDao;

  public TransitionService(TransitionDao transitionDao){
    this.transitionDao = transitionDao;
  }

  @Transactional(readOnly = true)
  public TransitionDto getCurrentTransitionByEmployeeId(Integer employeeId) {
    return new TransitionDto(transitionDao.getCurrentTransitionByEmployeeId(employeeId));
  }

  @Transactional
  public void setEmployeeNextTransition(Integer employeeId) {
    Transition transitionCurrent = transitionDao.getCurrentTransitionByEmployeeId(employeeId);
    Transition transitionNext = transitionDao.getRecordById(transitionCurrent.getNext().getId());

    transitionCurrent.setStepStatus(WorkflowStepStatus.DONE);
    transitionNext.setStepStatus(WorkflowStepStatus.CURRENT);

    transitionDao.update(transitionCurrent);
    transitionDao.update(transitionNext);
  }

  @Transactional(readOnly = true)
  public List<TransitionDto> getAllTransitionDtoByEmployeeId(Integer employeeId) {
    return transitionDao.getAllTransitionByEmployeeId(employeeId).stream().map(TransitionDto::new).collect(Collectors.toList());
  }
}
