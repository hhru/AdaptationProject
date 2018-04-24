package ru.hh.school.adaptation.dao;

import org.hibernate.SessionFactory;
import ru.hh.school.adaptation.entities.Comment;

import javax.inject.Inject;

public class CommentDao {
  private final SessionFactory sessionFactory;

  @Inject
  public CommentDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public void save(Comment comment) {
    sessionFactory.getCurrentSession().save(comment);
  }

  public void delete(Integer commentId){
    sessionFactory.getCurrentSession().createQuery("DELETE from Comment WHERE id = :commentId")
            .setParameter("commentId", commentId)
            .executeUpdate();
  }

}
