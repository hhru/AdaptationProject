package ru.hh.school.adaptation.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.hh.school.adaptation.entities.Employee;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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

    public void update(Employee employee) {
        sessionFactory.getCurrentSession().update(employee);
    }

}
