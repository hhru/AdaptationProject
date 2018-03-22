package ru.hh.school.adaptation.services;

import org.springframework.transaction.annotation.Transactional;
import ru.hh.school.adaptation.dao.TransitionDao;
import ru.hh.school.adaptation.dao.WorkflowStepDao;
import ru.hh.school.adaptation.dto.WorkflowStepDto;
import ru.hh.school.adaptation.entities.WorkflowStep;

import javax.inject.Singleton;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class WorkflowService {

  private TransitionDao transitionDao;
  private WorkflowStepDao workflowStepDao;

  public WorkflowService(TransitionDao transitionDao, WorkflowStepDao workflowStepDao){
    this.transitionDao = transitionDao;
    this.workflowStepDao = workflowStepDao;
  }

  @Transactional(readOnly = true)
  public WorkflowStep getWorkflowStep(Integer id) {
    return workflowStepDao.getRecordById(id);
  }

  @Transactional(readOnly = true)
  public List<WorkflowStepDto> getAllWorkflowSteps() {
    List<WorkflowStep> workflowStepList = workflowStepDao.getAllRecords();
    return workflowStepList.stream().map(WorkflowStepDto::new).collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<WorkflowStepDto> getAllWorkflowSteps(Integer workflowId) {
    List<WorkflowStep> workflowStepList = transitionDao.getAllRecords(workflowId);
    return workflowStepList.stream().map(WorkflowStepDto::new).collect(Collectors.toList());
  }

}
