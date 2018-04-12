package ru.hh.school.adaptation.dto;

import ru.hh.school.adaptation.entities.TaskForm;

import java.util.List;
import java.util.stream.Collectors;

public class TaskFormDto {

  public String key;

  public List<TaskDto> taskList;

  public TaskFormDto(){

  }

  public TaskFormDto(TaskForm taskForm){
    key = taskForm.getKey();
    if (taskForm.getTasks() != null){
      taskList = taskForm.getTasks().stream().map(TaskDto::new).collect(Collectors.toList());
    }
  }

}
