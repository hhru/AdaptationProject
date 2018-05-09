package ru.hh.school.adaptation.services;

import org.apache.commons.lang3.time.DateUtils;
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
  private WorkflowService workflowService;

  public TransitionService(TransitionDao transitionDao, WorkflowService workflowService){
    this.transitionDao = transitionDao;
    this.workflowService = workflowService;
  }

  @Transactional(readOnly = true)
  public TransitionDto getCurrentTransitionByEmployeeId(Integer employeeId) {
    Transition transition = transitionDao.getCurrentTransitionByEmployeeId(employeeId);
    if (transition==null) {
      return null;
    }
    return new TransitionDto(transition);
  }

  @Transactional
  public void setEmployeeNextTransition(Employee employee) {
    Transition transitionCurrent = transitionDao.getCurrentTransitionByEmployeeId(employee.getId());
    transitionCurrent.setStepStatus(WorkflowStepStatus.DONE);
    transitionDao.update(transitionCurrent);

    Transition transitionNext = null;
    if (transitionCurrent.getNext()!=null) {
      transitionNext = transitionDao.getRecordById(transitionCurrent.getNext().getId());
      transitionNext.setStepStatus(WorkflowStepStatus.CURRENT);
      transitionDao.update(transitionNext);
      workflowService.stepAction(employee, transitionNext.getStepType());
    }
  }

  @Transactional(readOnly = true)
  public List<TransitionDto> getAllTransitionDtoByEmployeeId(Integer employeeId) {
    return transitionDao.getAllTransitionByEmployeeId(employeeId).stream().map(TransitionDto::new).collect(Collectors.toList());
  }

  @Transactional
  public List<Transition> createTransitionsForNewEmployee(Employee employee) {
    List<Transition> transitions = new LinkedList<>();
    Transition prev = null;

    WorkflowStepType[] workflowIter = WorkflowStepType.values();
    for (int i = workflowIter.length - 1; i >= 0; i--) {
      WorkflowStepType workflowStepType = workflowIter[i];

      Transition transition = new Transition();
      transition.setNext(prev);
      transition.setEmployee(employee);
      transition.setStepType(workflowStepType);
      switch (workflowStepType) {
        case ADD:
          transition.setStepStatus(WorkflowStepStatus.DONE);
          break;
        case TASK_LIST:
          transition.setStepStatus(WorkflowStepStatus.CURRENT);
          break;
        default:
          transition.setStepStatus(WorkflowStepStatus.NOT_DONE);
      }
      switch (workflowStepType) {
        case WELCOME_MEETING:
          transition.setDeadlineTimestamp(employee.getEmploymentDate());
          break;
        case INTERIM_MEETING:
          transition.setDeadlineTimestamp(DateUtils.addMonths(DateUtils.addDays(employee.getEmploymentDate(), 1), 15));
          break;
        case FINAL_MEETING:
          transition.setDeadlineTimestamp(DateUtils.addMonths(employee.getEmploymentDate(), 3));
          break;
      }
      transitionDao.save(transition);
      transitions.add(transition);
      prev = transition;
    }
    workflowService.stepAction(employee, WorkflowStepType.ADD);
    return transitions;
  }
}
