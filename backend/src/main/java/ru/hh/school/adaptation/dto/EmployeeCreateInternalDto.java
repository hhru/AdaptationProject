package ru.hh.school.adaptation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.hh.school.adaptation.AdaptationCommonConfig;
import ru.hh.school.adaptation.entities.Gender;

import java.util.Date;

public class EmployeeCreateInternalDto {

  public PersonalDto self;

  public PersonalDto chief;

  public PersonalDto mentor;

  public Integer hrId;

  public String position;

  public Gender gender;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AdaptationCommonConfig.JSON_DATE_FORMAT)
  public Date employmentDate;

  public EmployeeCreateInternalDto(Integer hdId, EmployeeCreateDto employeeCreateDto) {
    self = employeeCreateDto.self;
    chief = employeeCreateDto.chief;
    mentor = employeeCreateDto.mentor;
    position = employeeCreateDto.position;
    gender = employeeCreateDto.gender;
    employmentDate = employeeCreateDto.employmentDate;
    this.hrId = hdId;
  }
}
