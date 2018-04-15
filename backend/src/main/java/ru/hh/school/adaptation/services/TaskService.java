package ru.hh.school.adaptation.services;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.xmlbeans.XmlException;
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
import ru.hh.school.adaptation.misc.Named;

import javax.inject.Singleton;
import javax.ws.rs.WebApplicationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Singleton
public class TaskService {

  private TaskFormDao taskFormDao;
  private TaskDao taskDao;
  private EmployeeDao employeeDao;
  private MailService mailService;
  private DocumentService documentService;

  public TaskService(TaskFormDao taskFormDao, TaskDao taskDao, EmployeeDao employeeDao, MailService mailService, DocumentService documentService) {
    this.taskFormDao = taskFormDao;
    this.taskDao = taskDao;
    this.employeeDao = employeeDao;
    this.mailService = mailService;
    this.documentService = documentService;
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
    notifyHr(taskForm.getEmployee());
    return new TaskFormDto(taskForm);
  }

  private void notifyHr(Employee employee){
    Map<String, String> params = new HashMap<>();
    String fio = String.format("%s %s %s",
        employee.getSelf().getLastName(),
        employee.getSelf().getFirstName(),
        employee.getSelf().getMiddleName());
    String taskUrl = String.format("https://www.adaptation.host/api/employee/tasks/%d/doc", employee.getId());
    params.put("{{userName}}", fio);
    params.put("{{taskUrl}}", taskUrl);
    mailService.sendMail(employee.getHr().getSelf().getEmail(), "hr_task_notify", params);
  }

  @Transactional
  public TaskForm createTaskForm(Employee employee) {
    TaskForm taskForm = employee.getTaskForm();
    if (taskForm == null) {
      taskForm = new TaskForm();
      taskForm.setEmployee(employee);
      taskForm.setKey(UUID.randomUUID().toString().replace("-", ""));
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

  @Transactional
  public Named<byte[]> generateTaskDoc(Integer employeeId) {
    try {
      return documentService.generateTaskDoc(employeeDao.getRecordById(employeeId));
    } catch (InvalidFormatException | IOException | XmlException | NullPointerException e) {
      throw new WebApplicationException("Bad document", e);
    }
  }
}
