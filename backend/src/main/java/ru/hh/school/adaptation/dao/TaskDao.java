package ru.hh.school.adaptation.dao;

import org.hibernate.SessionFactory;
import ru.hh.school.adaptation.entities.Task;

import javax.inject.Inject;

public class TaskDao {

  private final SessionFactory sessionFactory;

  @Inject
  public TaskDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public Task getRecordById(Integer id) {
    return sessionFactory.getCurrentSession().get(Task.class, id);
  }

  public void save(Task task) {
    sessionFactory.getCurrentSession().persist(task);
  }

  public void update(Task task) {
    sessionFactory.getCurrentSession().update(task);
  }


}
