package ru.hh.school.adaptation.services;

import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.school.adaptation.dao.CommentDao;
import ru.hh.school.adaptation.dao.LogDao;
import ru.hh.school.adaptation.dao.UserDao;
import ru.hh.school.adaptation.dto.CommentDto;
import ru.hh.school.adaptation.dto.CommentCreateDto;
import ru.hh.school.adaptation.entities.Comment;
import ru.hh.school.adaptation.entities.Log;
import ru.hh.school.adaptation.services.auth.AuthService;

import javax.inject.Singleton;

@Singleton
public class CommentService {
  private CommentDao commentDao;
  private LogDao logDao;
  private EmployeeService employeeService;
  private AuthService authService;
  private UserDao userDao;

  public CommentService(CommentDao commentDao, LogDao logDao, @Lazy EmployeeService employeeService, AuthService authService, UserDao userDao) {
    this.commentDao = commentDao;
    this.logDao = logDao;
    this.employeeService = employeeService;
    this.authService = authService;
    this.userDao = userDao;
  }

  @Transactional
  public CommentDto createCommentFromDto(CommentCreateDto commentCreateDto){
    Comment comment = new Comment();
    comment.setEmployee(employeeService.getEmployee(commentCreateDto.employeeId));
    comment.setAuthor(authService.getUser().get());
    comment.setMessage(commentCreateDto.message);

    commentDao.save(comment);

    return new CommentDto(comment, authService.getCurrentUserId());
  }

  @Transactional
  public void createComment(Comment comment){
    commentDao.save(comment);
  }

  @Transactional
  public void createLog(Log log){
    logDao.save(log);
  }

  @Transactional
  public void removeComment(Integer commentId){
    commentDao.delete(commentId);
  }

}
