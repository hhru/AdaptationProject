package ru.hh.school.adaptation.services;

import org.springframework.transaction.annotation.Transactional;
import ru.hh.school.adaptation.dao.EmployeeDao;
import ru.hh.school.adaptation.dao.UserDao;
import ru.hh.school.adaptation.dto.EmployeeBriefDto;
import ru.hh.school.adaptation.dto.EmployeeCreateDto;
import ru.hh.school.adaptation.dto.EmployeeDto;
import ru.hh.school.adaptation.dto.EmployeeUpdateDto;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.entities.Log;
import ru.hh.school.adaptation.entities.PersonalInfo;
import ru.hh.school.adaptation.entities.User;
import ru.hh.school.adaptation.exceptions.EntityNotFoundException;
import ru.hh.school.adaptation.exceptions.RequestValidationException;
import ru.hh.school.adaptation.services.auth.AuthService;

import javax.inject.Singleton;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class EmployeeService {

  private EmployeeDao employeeDao;
  private UserDao userDao;
  private PersonalInfoService personalInfoService;
  private TransitionService transitionService;
  private CommentService commentService;
  private AuthService authService;

  public EmployeeService(EmployeeDao employeeDao, UserDao userDao, PersonalInfoService personalInfoService, TransitionService transitionService,
                         CommentService commentService, AuthService authService) {
    this.employeeDao = employeeDao;
    this.authService = authService;
    this.userDao = userDao;
    this.personalInfoService = personalInfoService;
    this.transitionService = transitionService;
    this.commentService = commentService;
  }

  @Transactional(readOnly = true)
  public List<Employee> getAllEmployees() {
    return employeeDao.getAllRecords();
  }

  @Transactional(readOnly = true)
  public Employee getEmployee(Integer id) {
    Employee employee = employeeDao.getRecordById(id);
    if (employee == null) {
      throw new EntityNotFoundException(String.format("Employee with id = %d does not exist", id));
    }
    return employee;
  }

  @Transactional(readOnly = true)
  public List<EmployeeBriefDto> getBriefEmployeesList() {
    return getAllEmployees().stream().map(EmployeeBriefDto::new).collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public EmployeeDto getEmployeeDto(Integer id) {
    Employee employee = getEmployee(id);
    return new EmployeeDto(employee, authService.getCurrentUserId());
  }

  @Transactional
  public EmployeeDto createEmployee(EmployeeCreateDto employeeCreateDto){
    Employee employee = new Employee();
    employee.setSelf(personalInfoService.createPersonalInfo(employeeCreateDto.self));
    employee.setChief(personalInfoService.getOrCreatePersonalInfo(employeeCreateDto.chief));
    if (employeeCreateDto.mentor != null){
      employee.setMentor(personalInfoService.getOrCreatePersonalInfo(employeeCreateDto.mentor));
    }
    employee.setHr(userDao.getRecordById(employeeCreateDto.hrId));
    employee.setPosition(employeeCreateDto.position);
    employee.setGender(employeeCreateDto.gender);
    employee.setEmploymentDate(employeeCreateDto.employmentDate);
    employeeDao.save(employee);

    employee.setComments(null);
    employee.setWorkflow(transitionService.createTransitionsForNewEmployee(employee));

    return new EmployeeDto(employee, authService.getCurrentUserId());
  }

  @Transactional
  public EmployeeDto updateEmployee(EmployeeUpdateDto employeeUpdateDto){
    if(employeeUpdateDto.id == null){
      throw new RequestValidationException("Id in update request can't be null");
    } else {
      Employee employee = employeeDao.getRecordById(employeeUpdateDto.id);
      User hr = userDao.getRecordById(employeeUpdateDto.hrId);
      PersonalInfo mentor = null;
      if (employeeUpdateDto.mentor != null){
        mentor = personalInfoService.getOrCreatePersonalInfo(employeeUpdateDto.mentor);
      }
      PersonalInfo chief = personalInfoService.getOrCreatePersonalInfo(employeeUpdateDto.chief);

      employee.setEmploymentDate(employeeUpdateDto.employmentDate);
      employee.setGender(employeeUpdateDto.gender);
      employee.setHr(hr);
      employee.setPosition(employeeUpdateDto.position);
      employee.setMentor(mentor);
      employee.setChief(chief);

      PersonalInfo selfInfo = employee.getSelf();
      personalInfoService.updatePersonalInfo(selfInfo, employeeUpdateDto.self);
      employeeDao.update(employee);

      logEmployeeUpdate(employee, employeeUpdateDto, hr, mentor, chief);
      personalInfoService.logPersonalInfoUpdate(selfInfo, employeeUpdateDto.self, employee);

      return new EmployeeDto(employee, authService.getCurrentUserId());
    }
  }

  private void logEmployeeUpdate(Employee fromEmployee, EmployeeUpdateDto toEmployeeUpdateDto, User hr, PersonalInfo mentor, PersonalInfo chief) {
    String user = authService.getUser().map(u -> u.getSelf().getFirstName() + " " + u.getSelf().getLastName()).orElse("Anonymous");
    Log log = new Log();
    log.setEmployee(fromEmployee);
    log.setAuthor(user);
    log.setEventDate(new Date());

    if (fromEmployee.getEmploymentDate() != toEmployeeUpdateDto.employmentDate) {
      log.setMessage("Дата выхода на работу была изменена с " +
              fromEmployee.getEmploymentDate() +
              " на " +
              toEmployeeUpdateDto.employmentDate);
      commentService.createLog(log);
    }
    if (fromEmployee.getGender() != toEmployeeUpdateDto.gender) {
      log.setMessage("Пол был изменен с " +
              fromEmployee.getGender() +
              " на " +
              toEmployeeUpdateDto.gender);
      commentService.createLog(log);
    }
    if (fromEmployee.getHr() != hr) {
      log.setMessage("Сопровождающий hr был изменен с " +
              fromEmployee.getHr().getSelf().getFirstName() + " " + fromEmployee.getHr().getSelf().getLastName() +
              " на " +
              hr.getSelf().getFirstName() + " " + hr.getSelf().getLastName());
      commentService.createLog(log);
    }
    if (!fromEmployee.getPosition().equals(toEmployeeUpdateDto.position)) {
      log.setMessage("Позиция была изменена с " +
              fromEmployee.getPosition() +
              " на " +
              toEmployeeUpdateDto.position);
      commentService.createLog(log);
    }
    if (fromEmployee.getMentor() != mentor) {
      log.setMessage("Ментор был изменен с " +
              fromEmployee.getMentor().getFirstName() + " " + fromEmployee.getMentor().getLastName() +
              " на " +
              mentor.getFirstName() + " " + mentor.getLastName());
      commentService.createLog(log);
    }
    if (fromEmployee.getChief() != chief) {
      log.setMessage("Руководитель был изменен с " +
              fromEmployee.getChief().getFirstName() + " " + fromEmployee.getChief().getLastName() +
              " на " +
              chief.getFirstName() + " " + chief.getLastName());
      commentService.createLog(log);
    }
  }

}
