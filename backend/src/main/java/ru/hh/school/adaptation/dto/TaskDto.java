package ru.hh.school.adaptation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.hh.school.adaptation.AdaptationCommonConfig;
import ru.hh.school.adaptation.entities.Task;

import java.util.Date;

public class TaskDto {

  public Integer id;

  public String text;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AdaptationCommonConfig.JSON_DATE_FORMAT)
  public Date deadlineDate;

  public String resources;

  public Boolean deleted;

  public TaskDto(){

  }

  public TaskDto(Task task){
    id = task.getId();
    text = task.getText();
    deadlineDate = task.getDeadlineDate();
    resources = task.getResources();
    deleted = task.getDeleted();
  }

}
