package ru.hh.school.adaptation.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.hh.school.adaptation.entities.Employee;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

public class EmployeeDao {
  private final SessionFactory sessionFactory;
  private final UserDao userDao;
  private final WorkflowDao workflowDao;

  @Inject
  public EmployeeDao(SessionFactory sessionFactory, UserDao userDao, WorkflowDao workflowDao) {
    this.sessionFactory = sessionFactory;
    this.userDao = userDao;
    this.workflowDao = workflowDao;
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
    employee.setUpdateTimestamp(new Date());
    sessionFactory.getCurrentSession().update(employee);
  }

}
