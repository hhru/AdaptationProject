package ru.hh.school.adaptation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.hh.school.adaptation.AdaptationCommonConfig;
import ru.hh.school.adaptation.entities.WorkflowStepStatus;

import java.util.Date;

public class WorkflowStepDto {

  public int id;

  public String name;

  public WorkflowStepStatus status;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AdaptationCommonConfig.JSON_DATE_TIME_FORMAT)
  public Date deadlineTimestamp;

}
