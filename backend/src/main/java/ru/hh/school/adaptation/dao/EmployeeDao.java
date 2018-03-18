package ru.hh.school.adaptation.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.hh.school.adaptation.dto.EmployeeDto;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.entities.User;
import ru.hh.school.adaptation.exceptions.EntityDoesNotExistException;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
    CriteriaBuilder builder = session.getCriteriaBuilder();
    CriteriaQuery<Employee> criteriaQuery = builder.createQuery(Employee.class);
    Root<Employee> root = criteriaQuery.from(Employee.class);
    criteriaQuery.select(root);
    Query<Employee> query = session.createQuery(criteriaQuery);
    return query.getResultList();
  }

  public void save(Employee employee) {
    sessionFactory.getCurrentSession().persist(employee);
  }

  public void save(EmployeeDto employeeDto) {
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
    save(employee);
  }

  public void update(Employee employee) {
    employee.setUpdateTimestamp(new Date());
    sessionFactory.getCurrentSession().update(employee);
  }

  public void update(EmployeeDto employeeDto) {
    Employee employee = getRecordById(employeeDto.id);
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
    update(employee);
  }

}
