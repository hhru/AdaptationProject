package ru.hh.school.adaptation.dao;

import org.hibernate.SessionFactory;
import ru.hh.school.adaptation.entities.Employee;

import javax.inject.Inject;
import java.util.Date;

public class EmployeeDao {
  private final SessionFactory sessionFactory;

  @Inject
  public EmployeeDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public Employee getRecordById(Integer id) {
    return sessionFactory.getCurrentSession().get(Employee.class, id);
  }

  public void update(Employee employee) {
    employee.setUpdateTimestamp(new Date());
    sessionFactory.getCurrentSession().update(employee);
  }

}
