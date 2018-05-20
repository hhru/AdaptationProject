package ru.hh.school.adaptation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.hh.school.adaptation.AdaptationCommonConfig;
import ru.hh.school.adaptation.entities.Transition;
import ru.hh.school.adaptation.entities.WorkflowStepStatus;
import ru.hh.school.adaptation.entities.WorkflowStepType;

import java.util.Date;

public class WorkflowStepBriefDto {

  public Integer id;

  public WorkflowStepType type;

  public WorkflowStepStatus status;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AdaptationCommonConfig.JSON_DATE_TIME_FORMAT)
  public Date deadlineTimestamp;

  public boolean overdue;

  public WorkflowStepBriefDto(Transition transition) {
    id = transition.getId();
    type = transition.getStepType();
    status = transition.getStepStatus();
    deadlineTimestamp = transition.getDeadlineTimestamp();
    overdue = transition.getDeadlineTimestamp() != null
            && (new Date()).after(transition.getDeadlineTimestamp())
            && status == WorkflowStepStatus.CURRENT;
  }

}