package ru.hh.school.adaptation.services;

import org.springframework.transaction.annotation.Transactional;
import ru.hh.school.adaptation.dao.EmployeeDao;
import ru.hh.school.adaptation.dao.UserDao;
import ru.hh.school.adaptation.dto.EmployeeBriefDto;
import ru.hh.school.adaptation.dto.EmployeeCreateDto;
import ru.hh.school.adaptation.dto.EmployeeDto;
import ru.hh.school.adaptation.dto.EmployeeUpdateDto;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.entities.PersonalInfo;
import ru.hh.school.adaptation.exceptions.EntityNotFoundException;
import ru.hh.school.adaptation.exceptions.RequestValidationException;

import javax.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class EmployeeService {

  private EmployeeDao employeeDao;
  private UserDao userDao;
  private PersonalInfoService personalInfoService;
  private TransitionService transitionService;
  private CommentService commentService;

  public EmployeeService(EmployeeDao employeeDao, UserDao userDao, PersonalInfoService personalInfoService, TransitionService transitionService,
                         CommentService commentService) {
    this.employeeDao = employeeDao;
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
    return new EmployeeDto(getEmployee(id));
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

    return new EmployeeDto(employee);
  }

  @Transactional
  public EmployeeDto updateEmployee(EmployeeUpdateDto employeeUpdateDto){
    if(employeeUpdateDto.id == null){
      throw new RequestValidationException("Id in update request can't be null");
    } else {
      Employee employee = employeeDao.getRecordById(employeeUpdateDto.id);
      commentService.logEmployeeUpdate(employee, employeeUpdateDto);

      employee.setEmploymentDate(employeeUpdateDto.employmentDate);
      employee.setGender(employeeUpdateDto.gender);
      employee.setHr(userDao.getRecordById(employeeUpdateDto.hrId));
      employee.setPosition(employeeUpdateDto.position);

      if (employeeUpdateDto.mentor != null){
        employee.setMentor(personalInfoService.getOrCreatePersonalInfo(employeeUpdateDto.mentor));
      } else {
        employee.setMentor(null);
      }
      employee.setChief(personalInfoService.getOrCreatePersonalInfo(employeeUpdateDto.chief));

      PersonalInfo selfInfo = employee.getSelf();
      personalInfoService.updatePersonalInfo(selfInfo, employeeUpdateDto.self);

      employeeDao.update(employee);
      return new EmployeeDto(employee);
    }
  }

}
