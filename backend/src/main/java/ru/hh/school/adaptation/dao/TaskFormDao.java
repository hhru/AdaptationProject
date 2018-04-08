package ru.hh.school.adaptation.dao;

import org.hibernate.SessionFactory;
import ru.hh.school.adaptation.entities.TaskForm;
import ru.hh.school.adaptation.entities.User;

import javax.inject.Inject;

public class TaskFormDao {
  private final SessionFactory sessionFactory;

  @Inject
  public TaskFormDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public TaskForm getRecordById(Integer id) {
    return sessionFactory.getCurrentSession().get(TaskForm.class, id);
  }

  public TaskForm getRecordByEmployeeId(Integer employeeId) {
    return sessionFactory.getCurrentSession()
        .createQuery("from TaskForm T where T.employee.id=:employeeId", TaskForm.class)
        .setParameter("employeeId", employeeId)
        .uniqueResult();
  }

  public TaskForm getRecordByKey(String key) {
    return sessionFactory.getCurrentSession()
        .createQuery("from TaskForm T where T.key=:key", TaskForm.class)
        .setParameter("key", key)
        .uniqueResult();
  }

  public void save(TaskForm taskForm) {
    sessionFactory.getCurrentSession().persist(taskForm);
  }

  public void update(TaskForm taskForm) {
    sessionFactory.getCurrentSession().update(taskForm);
  }

}
