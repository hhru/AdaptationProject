package ru.hh.school.adaptation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.hh.school.adaptation.AdaptationCommonConfig;
import ru.hh.school.adaptation.entities.Log;

import java.util.Date;

public class LogDto {
  public Integer id;
  public Integer employeeId;
  public String author;
  public String message;
  public String link;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AdaptationCommonConfig.JSON_DATE_FORMAT)
  public Date eventDate;

  public LogDto() {
  }

  public LogDto(Log log){
    id = log.getId();
    employeeId = log.getEmployee().getId();
    author = log.getAuthor();
    message = log.getMessage();
    link = log.getLink();
    eventDate = log.getEventDate();
  }

}
