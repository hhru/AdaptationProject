package ru.hh.school.adaptation.services;

import org.springframework.transaction.annotation.Transactional;
import ru.hh.school.adaptation.dao.EmployeeDao;
import ru.hh.school.adaptation.dao.UserDao;
import ru.hh.school.adaptation.dao.WorkflowDao;
import ru.hh.school.adaptation.dto.EmployeeDto;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.entities.User;
import ru.hh.school.adaptation.exceptions.EntityDoesNotExistException;

import javax.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class EmployeeService {

  private EmployeeDao employeeDao;
  private UserDao userDao;
  private WorkflowDao workflowDao;

  public EmployeeService(EmployeeDao employeeDao, UserDao userDao, WorkflowDao workflowDao){
    this.employeeDao = employeeDao;
    this.userDao = userDao;
    this.workflowDao = workflowDao;
  }

  @Transactional
  public List<EmployeeDto> getAllEmployees() {
    List<Employee> employeeList = employeeDao.getAllRecords();
    return employeeList.stream().map(EmployeeDto::new).collect(Collectors.toList());
  }

  @Transactional
  public EmployeeDto getEmployee(Integer id) {
    Employee employee = employeeDao.getRecordById(id);
    if (employee == null) {
      throw new EntityDoesNotExistException("Employee with id = " + id + " does not exist");
    }
    return new EmployeeDto(employee);
  }

  @Transactional
  public void saveEmployee(EmployeeDto employeeDto) {
    Employee employee = new Employee();
    employee.setFirstName(employeeDto.firstName);
    employee.setLastName(employeeDto.lastName);
    employee.setMiddleName(employeeDto.middleName);
    employee.setEmail(employeeDto.email);
    employee.setGender(employeeDto.gender);
    employee.setPosition(employeeDto.position);
    employee.setEmploymentTimestamp(employeeDto.employmentTimestamp);
    employee.setMobilePhone(employeeDto.mobilePhone);
    employee.setInternalPhone(employeeDto.internalPhone);
    User user = userDao.getRecordById(employeeDto.curatorId);
    if (user == null) {
      throw new EntityDoesNotExistException("Curator with id = " + employeeDto.curatorId + " does not exist");
    }
    employee.setCurator(user);
    employee.setWorkflow(workflowDao.getRecordById(1));
    employeeDao.save(employee);
  }

  @Transactional
  public void updateEmployee(EmployeeDto employeeDto) {
    Employee employee = employeeDao.getRecordById(employeeDto.id);
    if (employeeDto.firstName != null) {
      employee.setFirstName(employeeDto.firstName);
    }
    if (employeeDto.lastName != null) {
      employee.setLastName(employeeDto.lastName);
    }
    if (employeeDto.middleName != null) {
      employee.setMiddleName(employeeDto.middleName);
    }
    if (employeeDto.email != null) {
      employee.setEmail(employeeDto.email);
    }
    if (employeeDto.mobilePhone != null) {
      employee.setMobilePhone(employeeDto.mobilePhone);
    }
    if (employeeDto.internalPhone != null) {
      employee.setInternalPhone(employeeDto.internalPhone);
    }
    if (employeeDto.gender != null) {
      employee.setGender(employeeDto.gender);
    }
    if (employeeDto.position != null) {
      employee.setPosition(employeeDto.position);
    }
    if (employeeDto.employmentTimestamp != null) {
      employee.setEmploymentTimestamp(employeeDto.employmentTimestamp);
    }
    if (employeeDto.curatorId != null) {
      User user = userDao.getRecordById(employeeDto.curatorId);
      if (user == null) {
        throw new EntityDoesNotExistException("Curator with id = " + employeeDto.curatorId + " does not exist");
      }
      employee.setCurator(user);
    }
    employeeDao.update(employee);
  }

}
