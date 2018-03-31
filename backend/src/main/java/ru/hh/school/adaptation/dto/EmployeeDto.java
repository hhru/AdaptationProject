package ru.hh.school.adaptation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.hh.school.adaptation.AdaptationCommonConfig;
import ru.hh.school.adaptation.entities.Employee;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeDto {

  public Integer id;

  public PersonalDto self;

  public PersonalDto chief;

  public PersonalDto mentor;

  public PersonalDto hr;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AdaptationCommonConfig.JSON_DATE_TIME_FORMAT)
  public Date employmentTimestamp;

  public List<WorkflowStepDto> workflow;

  public EmployeeDto(Employee employee){
    id = employee.getId();
    self = new PersonalDto(employee.getSelf());
    chief = new PersonalDto(employee.getChief());
    mentor = new PersonalDto(employee.getMentor());
    hr = new PersonalDto(employee.getHr());
    employmentTimestamp = employee.getEmploymentTimestamp();
    workflow = employee.getWorkflow().stream().map(WorkflowStepDto::new).collect(Collectors.toList());
  }

}
