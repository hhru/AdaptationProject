package ru.hh.school.adaptation.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.hh.school.adaptation.dto.EmployeeDto;
import ru.hh.school.adaptation.entities.Employee;

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
        employee.setCuratorId(userDao.getRecordById(employeeDto.curatorId));
        employee.setWorkflowId(workflowDao.getRecordById(employeeDto.workflowId));
        save(employee);
    }

    public void update(Employee employee) {
        employee.setUpdateTimestamp(new Date());
        sessionFactory.getCurrentSession().update(employee);
    }

    public void update(EmployeeDto employeeDto) {
        Employee employee = getRecordById(employeeDto.id);
        if(employeeDto.firstName != null) {
            employee.setFirstName(employeeDto.firstName);
        }
        if(employeeDto.lastName != null) {
            employee.setLastName(employeeDto.lastName);
        }
        if(employeeDto.middleName != null) {
            employee.setMiddleName(employeeDto.middleName);
        }
        if(employeeDto.email != null) {
            employee.setEmail(employeeDto.email);
        }
        if(employeeDto.gender != null) {
            employee.setGender(employeeDto.gender);
        }
        if(employeeDto.position != null) {
            employee.setPosition(employeeDto.position);
        }
        if(employeeDto.employmentTimestamp != null) {
            employee.setEmploymentTimestamp(employeeDto.employmentTimestamp);
        }
        if(employeeDto.curatorId != null) {
            employee.setCuratorId(userDao.getRecordById(employeeDto.curatorId));
        }
        if(employeeDto.workflowId != null) {
            employee.setWorkflowId(workflowDao.getRecordById(employeeDto.workflowId));
        }
        update(employee);
    }

}
