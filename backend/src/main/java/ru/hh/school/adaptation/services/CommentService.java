package ru.hh.school.adaptation.services;

import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.school.adaptation.dao.CommentDao;
import ru.hh.school.adaptation.dao.LogDao;
import ru.hh.school.adaptation.dao.UserDao;
import ru.hh.school.adaptation.dto.CommentCreateDto;
import ru.hh.school.adaptation.dto.EmployeeUpdateDto;
import ru.hh.school.adaptation.entities.Comment;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.entities.Log;
import ru.hh.school.adaptation.services.auth.AuthService;

import javax.inject.Singleton;
import java.util.Date;

@Singleton
public class CommentService {
  private CommentDao commentDao;
  private LogDao logDao;
  private EmployeeService employeeService;
  private AuthService authService;
  private UserDao userDao;
  private PersonalInfoService personalInfoService;

  public CommentService(CommentDao commentDao, LogDao logDao, @Lazy EmployeeService employeeService, AuthService authService, UserDao userDao,
                        PersonalInfoService personalInfoService){
    this.commentDao = commentDao;
    this.logDao = logDao;
    this.employeeService = employeeService;
    this.authService = authService;
    this.userDao = userDao;
    this.personalInfoService = personalInfoService;
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
  public void createLog(Log log){
    logDao.save(log);
  }

  @Transactional
  public void removeComment(Integer commentId){
    commentDao.delete(commentId);
  }

  public void logEmployeeUpdate(Employee fromEmployee, EmployeeUpdateDto toEmployeeUpdateDto) {
    String user = authService.getUser().map(u -> u.getSelf().getFirstName() + " " + u.getSelf().getLastName()).orElse("Вы");
    Log log = new Log();
    log.setEmployee(fromEmployee);
    log.setAuthor(user);
    log.setEventDate(new Date());

    if (fromEmployee.getEmploymentDate() != toEmployeeUpdateDto.employmentDate) {
      log.setMessage("Дата выхода на работу была изменена");
      createLog(log);
    }
    if (fromEmployee.getGender() != toEmployeeUpdateDto.gender) {
      log.setMessage("Пол был изменен");
      createLog(log);
    }
    if (fromEmployee.getHr() != userDao.getRecordById(toEmployeeUpdateDto.hrId)) {
      log.setMessage("Сопровождающий hr был изменен");
      createLog(log);
    }
    if (!fromEmployee.getPosition().equals(toEmployeeUpdateDto.position)) {
      log.setMessage("Позиция была изменена");
      createLog(log);
    }
    if (fromEmployee.getMentor() != personalInfoService.getPersonalInfo(toEmployeeUpdateDto.mentor)) {
      log.setMessage("Ментор был изменен");
      createLog(log);
    }
    if (fromEmployee.getChief() != personalInfoService.getPersonalInfo(toEmployeeUpdateDto.chief)) {
      log.setMessage("Руководитель был изменен");
      createLog(log);
    }
    if (fromEmployee.getSelf() != personalInfoService.getPersonalInfo(toEmployeeUpdateDto.self)) {
      log.setMessage("Личные данные были изменены");
      createLog(log);
    }
  }

}
