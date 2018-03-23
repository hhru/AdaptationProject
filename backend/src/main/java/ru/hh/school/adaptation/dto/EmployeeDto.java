package ru.hh.school.adaptation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.hh.school.adaptation.AdaptationCommonConfig;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.entities.Gender;

import java.util.Date;

public class EmployeeDto {

  public Integer id;

  public String firstName;

  public String lastName;

  public String middleName;

  public String position;

  public String email;

  public Long mobilePhone;

  public Integer internalPhone;

  public Gender gender;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AdaptationCommonConfig.JSON_DATE_TIME_FORMAT)
  public Date employmentTimestamp;

  public Integer workflowStepId;

  public Integer workflowId;

  public Integer mentorId;

  public Integer chiefId;

  public EmployeeDto() {

  }

  public EmployeeDto(Employee employee) {
    id = employee.getId();
    firstName = employee.getFirstName();
    lastName = employee.getLastName();
    middleName = employee.getMiddleName();
    position = employee.getPosition();
    email = employee.getEmail();
    gender = employee.getGender();
    employmentTimestamp = employee.getEmploymentTimestamp();
    workflowStepId = employee.getWorkflowStep().getId();
    workflowId = employee.getWorkflow().getId();
    mentorId = employee.getMentor().getId();
    chiefId = employee.getChief().getId();
  }

}
