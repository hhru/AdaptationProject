package ru.hh.school.adaptation.services;

import org.springframework.transaction.annotation.Transactional;
import ru.hh.school.adaptation.dao.TransitionDao;
import ru.hh.school.adaptation.dao.WorkflowDao;
import ru.hh.school.adaptation.dto.WorkflowDto;
import ru.hh.school.adaptation.entities.Workflow;

import javax.inject.Singleton;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class WorkflowService {

  private TransitionDao transitionDao;
  private WorkflowDao workflowDao;

  public WorkflowService(TransitionDao transitionDao, WorkflowDao workflowDao){
    this.transitionDao = transitionDao;
    this.workflowDao = workflowDao;
  }

  @Transactional
  public Workflow getWorkflow(Integer id) {
    return workflowDao.getRecordById(id);
  }

  @Transactional
  public List<WorkflowDto> getAllWorkflows() {
    List<Workflow> workflowList = workflowDao.getAllRecords();
    return workflowList.stream().map(WorkflowDto::new).collect(Collectors.toList());
  }

  @Transactional
  public List<WorkflowDto> getAllWorkflows(Integer workflowSetId) {
    List<Workflow> workflowList = transitionDao.getAllRecordsBySetId(workflowSetId);
    return workflowList.stream().map(WorkflowDto::new).collect(Collectors.toList());
  }

}
