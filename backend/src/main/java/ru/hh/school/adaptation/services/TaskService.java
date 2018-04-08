package ru.hh.school.adaptation.services;

import org.springframework.transaction.annotation.Transactional;
import ru.hh.school.adaptation.dao.EmployeeDao;
import ru.hh.school.adaptation.dao.TaskDao;
import ru.hh.school.adaptation.dao.TaskFormDao;
import ru.hh.school.adaptation.dto.TaskDto;
import ru.hh.school.adaptation.dto.TaskFormDto;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.entities.Task;
import ru.hh.school.adaptation.entities.TaskForm;
import ru.hh.school.adaptation.exceptions.EntityNotFoundException;

import javax.inject.Singleton;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

@Singleton
public class TaskService {

  private TaskFormDao taskFormDao;
  private TaskDao taskDao;
  private EmployeeDao employeeDao;

  public TaskService(TaskFormDao taskFormDao, TaskDao taskDao, EmployeeDao employeeDao) {
    this.taskFormDao = taskFormDao;
    this.taskDao = taskDao;
    this.employeeDao = employeeDao;
  }

  @Transactional
  public TaskFormDto submitTaskForm(TaskFormDto taskFormDto){
    TaskForm taskForm = taskFormDao.getRecordByKey(taskFormDto.key);
    List<Task> taskList = new LinkedList<>();
    for(TaskDto taskDto : taskFormDto.taskList){
      if (taskDto != null){
        Task task;
        if(taskDto.id == null){
          if (!taskDto.deleted){
            task = new Task();
            task.setText(taskDto.text);
            task.setDeadlineDate(taskDto.deadlineDate);
            task.setResources(taskDto.resources);
            task.setTaskForm(taskForm);
            task.setDeleted(false);
            taskDao.save(task);
            taskList.add(task);
          }
        } else {
          task = taskDao.getRecordById(taskDto.id);
          task.setText(taskDto.text);
          task.setDeadlineDate(taskDto.deadlineDate);
          task.setResources(taskDto.resources);
          task.setDeleted(taskDto.deleted);
          taskDao.update(task);
          taskList.add(task);
        }
      }
    }
    taskForm.setTasks(taskList);
    return new TaskFormDto(taskForm);
  }

  @Transactional
  public TaskForm createTaskForm(Integer employeeId) {
    Employee employee = employeeDao.getRecordById(employeeId);
    TaskForm taskForm = employee.getTaskForm();
    if (taskForm == null) {
      taskForm = new TaskForm();
      taskForm.setEmployee(employee);
      taskForm.setKey(UUID.randomUUID().toString().replace("-",""));
      taskFormDao.save(taskForm);
    }
    return taskForm;
  }

  @Transactional(readOnly = true)
  public TaskForm getTasksByEmployee(Integer employeeId) {
    return employeeDao.getRecordById(employeeId).getTaskForm();
  }

  @Transactional(readOnly = true)
  public TaskFormDto getTasksDtoByEmployee(Integer employeeId) {
    return new TaskFormDto(getTasksByEmployee(employeeId));
  }

  @Transactional(readOnly = true)
  public TaskForm getTasksByKey(String key) {
    TaskForm taskForm = taskFormDao.getRecordByKey(key);
    if (taskForm == null) {
      throw new EntityNotFoundException(String.format("Form with key=%s is not exist", key));
    }
    return taskForm;
  }

  @Transactional(readOnly = true)
  public TaskFormDto getTasksDtoByKey(String key) {
    return new TaskFormDto(getTasksByKey(key));
  }
}
