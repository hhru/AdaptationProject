package ru.hh.school.adaptation.services;

import org.springframework.transaction.annotation.Transactional;
import ru.hh.school.adaptation.dao.TransitionDao;
import ru.hh.school.adaptation.dto.TransitionDto;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.entities.Transition;
import ru.hh.school.adaptation.entities.WorkflowStepStatus;
import ru.hh.school.adaptation.entities.WorkflowStepType;

import javax.inject.Singleton;
import java.util.LinkedList;
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

  @Transactional
  public List<Transition> createTransitionsForNewEmployee(Employee employee) {
    List<Transition> transitions = new LinkedList<>();
    for(WorkflowStepType workflowStepType : WorkflowStepType.values()){
      Transition transition = new Transition();
      transition.setEmployee(employee);
      transition.setStepType(workflowStepType);
      transition.setStepStatus(WorkflowStepStatus.NOT_DONE);
      transitionDao.save(transition);
      transitions.add(transition);
    }
    return transitions;
  }
}
