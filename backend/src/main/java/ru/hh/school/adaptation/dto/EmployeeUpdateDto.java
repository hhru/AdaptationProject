package ru.hh.school.adaptation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.hh.school.adaptation.AdaptationCommonConfig;
import ru.hh.school.adaptation.entities.Gender;

import java.util.Date;

public class EmployeeUpdateDto {

  public Integer id;

  public PersonalDto self;

  public PersonalDto chief;

  public PersonalDto mentor;

  public Integer hrId;

  public String position;

  public Gender gender;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AdaptationCommonConfig.JSON_DATE_FORMAT)
  public Date employmentTimestamp;

}
