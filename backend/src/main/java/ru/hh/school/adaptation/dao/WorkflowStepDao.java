package ru.hh.school.adaptation.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import ru.hh.school.adaptation.dto.WorkflowStepDto;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.entities.WorkflowStep;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class WorkflowStepDao {
  private final SessionFactory sessionFactory;

  @Inject
  public WorkflowStepDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public WorkflowStep getRecordById(Integer id) {
    return sessionFactory.getCurrentSession().get(WorkflowStep.class, id);
  }

  public List<WorkflowStep> getAllRecords() {
    Session session = sessionFactory.getCurrentSession();
    return session.createQuery("from WorkflowStep", WorkflowStep.class).list();
  }

  public List<WorkflowStepDto> getAllWorkflowStepsByEmployeeId(Integer employeeId) {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    Employee employee = (Employee) session.load(Employee.class, employeeId);
	List<WorkflowStep> workflowStepList = session.createQuery("SELECT T.workflowStep from Transition T "
                    + "where T.workflow=:workflow order by T.workflowStep", WorkflowStep.class)
                    .setParameter("workflow", employee.getWorkflow())
                    .list();
    session.getTransaction().commit();
    return workflowStepList.stream().map(WorkflowStepDto::new).collect(Collectors.toList());
  }

}
