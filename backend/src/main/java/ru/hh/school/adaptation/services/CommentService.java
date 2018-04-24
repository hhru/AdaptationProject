package ru.hh.school.adaptation.services;

import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.school.adaptation.dao.CommentDao;
import ru.hh.school.adaptation.dto.CommentCreateDto;
import ru.hh.school.adaptation.entities.Comment;

import javax.inject.Singleton;

@Singleton
public class CommentService {
  private CommentDao commentDao;
  private EmployeeService employeeService;

  public CommentService(CommentDao commentDao, @Lazy EmployeeService employeeService){
    this.commentDao = commentDao;
    this.employeeService = employeeService;
  }

  @Transactional
  public Integer createCommentFromDto(CommentCreateDto commentCreateDto){
    Comment comment = new Comment();
    comment.setEmployee(employeeService.getEmployee(commentCreateDto.employeeId));
    comment.setAuthor(commentCreateDto.author);
    comment.setMessage(commentCreateDto.message);

    commentDao.save(comment);

    return comment.getId();
  }

  @Transactional
  public void createComment(Comment comment){
    commentDao.save(comment);
  }

  @Transactional
  public void removeComment(Integer commentId){
    commentDao.delete(commentId);
  }

}
