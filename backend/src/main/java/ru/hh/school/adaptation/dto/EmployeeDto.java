package ru.hh.school.adaptation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.hh.school.adaptation.ProdConfig;
import ru.hh.school.adaptation.entities.Employee;

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

  public String gender;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ProdConfig.JSON_DATE_TIME_FORMAT)
  public Date employmentTimestamp;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ProdConfig.JSON_DATE_TIME_FORMAT)
  public Date updateTimestamp;

  public Integer curatorId;

  public Integer workflowId;

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
    updateTimestamp = employee.getUpdateTimestamp();
    curatorId = employee.getCurator().getId();
    workflowId = employee.getWorkflow().getId();
  }

}
