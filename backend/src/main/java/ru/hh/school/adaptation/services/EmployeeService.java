package ru.hh.school.adaptation.services;

import org.springframework.transaction.annotation.Transactional;
import ru.hh.school.adaptation.dao.EmployeeDao;
import ru.hh.school.adaptation.entities.Employee;

import javax.inject.Singleton;

@Singleton
public class EmployeeService {

  private EmployeeDao employeeDao;

  public EmployeeService(EmployeeDao employeeDao){
    this.employeeDao = employeeDao;
  }

  public Employee getEmployee(Integer id) {
    return employeeDao.getRecordById(id);
  }

  @Transactional
  public void updateEmployee(Employee employee) {
    employeeDao.update(employee);
  }
}
