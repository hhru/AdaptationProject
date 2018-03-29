package ru.hh.school.adaptation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.hh.school.adaptation.AdaptationCommonConfig;

import java.util.Date;
import java.util.List;

public class EmployeeDto {

  public Integer id;

  public PersonalDto employee;

  public PersonalDto chief;

  public PersonalDto mentor;

  public PersonalDto hr;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AdaptationCommonConfig.JSON_DATE_TIME_FORMAT)
  public Date employmentTimestamp;

  public List<WorkflowStepDto> workflow;

}
