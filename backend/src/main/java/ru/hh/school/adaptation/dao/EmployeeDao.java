package ru.hh.school.adaptation.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.hh.school.adaptation.entities.Employee;

import javax.inject.Inject;
import java.util.List;

public class EmployeeDao {
  private final SessionFactory sessionFactory;

  @Inject
  public EmployeeDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public Employee getRecordById(Integer id) {
    return sessionFactory.getCurrentSession().get(Employee.class, id);
  }

  public List<Employee> getAllRecords() {
    Session session = sessionFactory.getCurrentSession();
    return session.createQuery("from Employee", Employee.class).list();
  }

  public void save(Employee employee) {
    sessionFactory.getCurrentSession().persist(employee);
  }

  public void update(Employee employee) {
    sessionFactory.getCurrentSession().update(employee);
  }

  public Employee getEmployeeWithTaskForm(Integer employeeId) {
    Employee employee = sessionFactory.getCurrentSession().get(Employee.class, employeeId);

    if (employee != null) {
      employee.setTaskForm(employee.getTaskForm());
    }

    return employee;
  }

}
