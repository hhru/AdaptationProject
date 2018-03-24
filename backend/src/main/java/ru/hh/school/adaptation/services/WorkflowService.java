package ru.hh.school.adaptation.services;

import org.springframework.transaction.annotation.Transactional;
import ru.hh.school.adaptation.dao.WorkflowStepDao;
import ru.hh.school.adaptation.dto.WorkflowStepDto;
import ru.hh.school.adaptation.entities.WorkflowStep;

import javax.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class WorkflowService {
  private WorkflowStepDao workflowStepDao;

  public WorkflowService(WorkflowStepDao workflowStepDao){
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

  public List<WorkflowStepDto> getAllWorkflowStepsByEmployeeId(Integer employeeId) {
    List<WorkflowStepDto> workflowStepList = workflowStepDao.getAllWorkflowStepsByEmployeeId(employeeId);
    return workflowStepList;
  }

}
