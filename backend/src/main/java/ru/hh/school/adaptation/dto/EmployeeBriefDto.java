package ru.hh.school.adaptation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.hh.school.adaptation.AdaptationCommonConfig;
import ru.hh.school.adaptation.entities.Employee;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeBriefDto {

  public Integer id;

  public String firstName;

  public String lastName;

  public String middleName;

  public String hrName;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AdaptationCommonConfig.JSON_DATE_FORMAT)
  public Date employmentDate;

  public List<WorkflowStepBriefDto> workflow;

  public EmployeeBriefDto(Employee employee) {
    id = employee.getId();
    firstName = employee.getSelf().getFirstName();
    lastName = employee.getSelf().getLastName();
    middleName = employee.getSelf().getMiddleName();
    employmentDate = employee.getEmploymentDate();
    hrName = String.format("%s %s",
        employee.getHr().getSelf().getLastName(),
        employee.getHr().getSelf().getFirstName()
    );
    workflow = employee.getWorkflow().stream().map(WorkflowStepBriefDto::new).collect(Collectors.toList());
  }
}
