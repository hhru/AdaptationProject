package ru.hh.school.adaptation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.hh.school.adaptation.AdaptationCommonConfig;

import java.util.Date;

public class LogCreateDto {
  public Integer employeeId;
  public String author;
  public String message;
  public String link;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AdaptationCommonConfig.JSON_DATE_FORMAT)
  public Date eventDate;
}
