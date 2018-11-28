package ru.hh.school.adaptation.dto;

import ru.hh.school.adaptation.entities.Task;

public class TaskDto {

  public Integer id;

  public String text;

  public String deadline;

  public Boolean isWeeks;

  public String resources;

  public Boolean deleted;

  public TaskDto(){

  }

  public TaskDto(Task task){
    id = task.getId();
    text = task.getText();
    deadline = task.getDeadline();
    isWeeks = task.getIsWeeks();
    resources = task.getResources();
    deleted = task.getDeleted();
  }

}
