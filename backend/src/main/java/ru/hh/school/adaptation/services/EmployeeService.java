package ru.hh.school.adaptation.services;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.xmlbeans.XmlException;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.nab.core.util.FileSettings;
import ru.hh.school.adaptation.dao.EmployeeDao;
import ru.hh.school.adaptation.dao.UserDao;
import ru.hh.school.adaptation.dto.EmployeeBriefDto;
import ru.hh.school.adaptation.dto.EmployeeCreateInternalDto;
import ru.hh.school.adaptation.dto.EmployeeDto;
import ru.hh.school.adaptation.dto.EmployeeUpdateDto;
import ru.hh.school.adaptation.entities.Comment;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.entities.Log;
import ru.hh.school.adaptation.entities.PersonalInfo;
import ru.hh.school.adaptation.entities.User;
import ru.hh.school.adaptation.exceptions.EntityNotFoundException;
import ru.hh.school.adaptation.exceptions.RequestValidationException;
import ru.hh.school.adaptation.misc.CommonUtils;
import ru.hh.school.adaptation.services.auth.AuthService;
import ru.hh.school.adaptation.misc.Named;
import ru.hh.school.adaptation.services.documents.ProbationResultDocumentGenerator;

import javax.inject.Singleton;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class EmployeeService {

  private String adaptationHost;

  private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

  private EmployeeDao employeeDao;
  private UserDao userDao;
  private PersonalInfoService personalInfoService;
  private TransitionService transitionService;
  private CommentService commentService;
  private ProbationResultDocumentGenerator probationResultDocumentGenerator;
  private AuthService authService;
  private MailService mailService;

  public EmployeeService(FileSettings fileSettings, EmployeeDao employeeDao, UserDao userDao, PersonalInfoService personalInfoService,
                         TransitionService transitionService, ProbationResultDocumentGenerator probationResultDocumentGenerator,
                         CommentService commentService, AuthService authService, MailService mailService) {
    this.employeeDao = employeeDao;
    this.authService = authService;
    this.userDao = userDao;
    this.personalInfoService = personalInfoService;
    this.transitionService = transitionService;
    this.commentService = commentService;
    this.probationResultDocumentGenerator = probationResultDocumentGenerator;
    this.mailService = mailService;

    adaptationHost = fileSettings.getProperties().getProperty("adaptation.host");
  }

  @Transactional(readOnly = true)
  public List<Employee> getAllEmployees() {
    return employeeDao.getAllRecords();
  }

  @Transactional(readOnly = true)
  public Employee getEmployee(Integer id) {
    Employee employee = employeeDao.getRecordById(id);
    if (employee == null) {
      throw new EntityNotFoundException(String.format("Employee with id = %d does not exist", id));
    }
    return employee;
  }

  @Transactional(readOnly = true)
  public List<EmployeeBriefDto> getBriefEmployeesList() {
    return getAllEmployees().stream().map(EmployeeBriefDto::new).collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public EmployeeDto getEmployeeDto(Integer id) {
    Employee employee = getEmployee(id);
    return new EmployeeDto(employee, authService.getCurrentUserId());
  }

  @Transactional
  public EmployeeDto createEmployee(EmployeeCreateInternalDto employeeCreateInternalDto){
    Employee employee = new Employee();
    employee.setSelf(personalInfoService.createPersonalInfo(employeeCreateInternalDto.self));
    employee.setChief(personalInfoService.getOrCreatePersonalInfo(employeeCreateInternalDto.chief));
    if (employeeCreateInternalDto.mentor != null){
      employee.setMentor(personalInfoService.getOrCreatePersonalInfo(employeeCreateInternalDto.mentor));
    }
    employee.setHr(userDao.getRecordById(employeeCreateInternalDto.hrId));
    employee.setPosition(employeeCreateInternalDto.position);
    employee.setGender(employeeCreateInternalDto.gender);
    employee.setEmploymentDate(employeeCreateInternalDto.employmentDate);
    employeeDao.save(employee);

    employee.setComments(null);
    employee.setWorkflow(transitionService.createTransitionsForNewEmployee(employee));

    return new EmployeeDto(employee, authService.getCurrentUserId());
  }

  @Transactional
  public EmployeeDto updateEmployee(EmployeeUpdateDto employeeUpdateDto){
    if(employeeUpdateDto.id == null){
      throw new RequestValidationException("Id in update request can't be null");
    } else {
      Employee employee = employeeDao.getRecordById(employeeUpdateDto.id);
      User hr = userDao.getRecordById(employeeUpdateDto.hrId);
      PersonalInfo mentor = null;
      if (employeeUpdateDto.mentor != null){
        mentor = personalInfoService.getOrCreatePersonalInfo(employeeUpdateDto.mentor);
      }
      PersonalInfo chief = personalInfoService.getOrCreatePersonalInfo(employeeUpdateDto.chief);
      PersonalInfo selfInfo = employee.getSelf();

      logEmployeeUpdate(employee, employeeUpdateDto, hr, mentor, chief);
      personalInfoService.logPersonalInfoUpdate(selfInfo, employeeUpdateDto.self, employee);

      employee.setGender(employeeUpdateDto.gender);
      employee.setEmploymentDate(employeeUpdateDto.employmentDate);
      if (employee.getHr() != hr) {
        notifyNewHr(hr, employee);
      }
      employee.setHr(hr);
      employee.setPosition(employeeUpdateDto.position);
      employee.setMentor(mentor);
      employee.setChief(chief);

      personalInfoService.updatePersonalInfo(selfInfo, employeeUpdateDto.self);
      employeeDao.update(employee);

      return new EmployeeDto(employee, authService.getCurrentUserId());
    }
  }

  private void logEmployeeUpdate(Employee fromEmployee, EmployeeUpdateDto toEmployeeUpdateDto, User hr, PersonalInfo mentor, PersonalInfo chief) {
    String user = authService.getUser().map(u -> u.getSelf().getFirstName() + " " + u.getSelf().getLastName()).orElse("Anonymous");
    Log log = new Log();
    log.setEmployee(fromEmployee);
    log.setAuthor(user);
    log.setEventDate(new Date());

    if (fromEmployee.getEmploymentDate() != toEmployeeUpdateDto.employmentDate) {
      log.setMessage("Дата выхода на работу была изменена с " +
              fromEmployee.getEmploymentDate() +
              " на " +
              toEmployeeUpdateDto.employmentDate);
      commentService.createLog(log);
    }
    if (fromEmployee.getGender() != toEmployeeUpdateDto.gender) {
      log.setMessage("Пол был изменен с " +
              fromEmployee.getGender() +
              " на " +
              toEmployeeUpdateDto.gender);
      commentService.createLog(log);
    }
    if (fromEmployee.getHr() != hr) {
      log.setMessage("Сопровождающий hr был изменен с " +
              fromEmployee.getHr().getSelf().getFirstName() + " " + fromEmployee.getHr().getSelf().getLastName() +
              " на " +
              hr.getSelf().getFirstName() + " " + hr.getSelf().getLastName());
      commentService.createLog(log);
    }
    if (!fromEmployee.getPosition().equals(toEmployeeUpdateDto.position)) {
      log.setMessage("Позиция была изменена с " +
              fromEmployee.getPosition() +
              " на " +
              toEmployeeUpdateDto.position);
      commentService.createLog(log);
    }
    if (fromEmployee.getMentor() != mentor) {
      log.setMessage("Ментор был изменен с " +
              fromEmployee.getMentor().getFirstName() + " " + fromEmployee.getMentor().getLastName() +
              " на " +
              mentor.getFirstName() + " " + mentor.getLastName());
      commentService.createLog(log);
    }
    if (fromEmployee.getChief() != chief) {
      log.setMessage("Руководитель был изменен с " +
              fromEmployee.getChief().getFirstName() + " " + fromEmployee.getChief().getLastName() +
              " на " +
              chief.getFirstName() + " " + chief.getLastName());
      commentService.createLog(log);
    }
  }

  private void notifyNewHr(User hr, Employee employee) {
    DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    String hrEmail = hr.getSelf().getEmail();

    Date now = new Date();
    Date interimDate = DateUtils.addDays(DateUtils.addMonths(employee.getEmploymentDate(), 1), 15);
    Date finalDate = DateUtils.addMonths(employee.getEmploymentDate(), 3);

    String subject="Сопровождение нового сотрудника";
    String messageBody="Привет. Ты был назначен на сопровождение нового сотрудника. " +
            employee.getSelf().getFirstName() + " " + employee.getSelf().getLastName() +
            " рассчитывает на твою поддержку в прохождении испытательного срока. " +
            "Более подробную информацию ты можешь получить по ссылке " +
            "https://" + adaptationHost + "/employee/" + employee.getId();
    mailService.sendMail(hrEmail, messageBody, subject);

    if (interimDate.after(now)) {
      mailService.sendCalendar(hrEmail, "Промежуточная встреча", dateFormat.format(interimDate));
    }
    if (finalDate.after(now)) {
      mailService.sendCalendar(hrEmail, "Итоговая встреча", dateFormat.format(finalDate));
    }
  }

  @Transactional
  public Response generateProbationResultDoc(Integer employeeId, String userAgent) {
    try {
      Named<byte[]> doc = probationResultDocumentGenerator.generateDoc(employeeDao.getRecordById(employeeId));

      return Response.ok(doc.get()).header(
          "Content-Disposition", String.format("attachment; filename=\"%s.docx\"",
            CommonUtils.getContentDispositionFilename(userAgent, doc.name())
          )
        ).build();
    } catch (InvalidFormatException | IOException | XmlException | NullPointerException e) {
      logger.error("Bad document for employer id {} ", employeeId);
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }

  @Transactional
  public void dismissEmployee(Integer id, String dismissComment) {
    String user = authService.getUser().map(u -> u.getSelf().getFirstName() + " " + u.getSelf().getLastName()).orElse("Anonymous");
    Employee employee = employeeDao.getRecordById(id);
    employee.setDismissed(true);
    employeeDao.update(employee);

    Log log = new Log();
    log.setEmployee(employee);
    log.setAuthor(user);
    log.setEventDate(new Date());
    log.setMessage("Сотрудник был уволен");
    commentService.createLog(log);

    if (dismissComment != null && !dismissComment.equals("")) {
      Comment comment = new Comment();
      comment.setEmployee(employee);
      comment.setAuthor(authService.getUser().get());
      comment.setMessage(dismissComment);
      commentService.createComment(comment);
    }
  }

  @Transactional
  public void undismissEmployee(Integer id, String dismissComment) {
    String user = authService.getUser().map(u -> u.getSelf().getFirstName() + " " + u.getSelf().getLastName()).orElse("Anonymous");
    Employee employee = employeeDao.getRecordById(id);
    employee.setDismissed(false);
    employeeDao.update(employee);

    Log log = new Log();
    log.setEmployee(employee);
    log.setAuthor(user);
    log.setEventDate(new Date());
    log.setMessage("Сотрудник был восстановлен");
    commentService.createLog(log);

    if (dismissComment != null && !dismissComment.equals("")) {
      Comment comment = new Comment();
      comment.setEmployee(employee);
      comment.setAuthor(authService.getUser().get());
      comment.setMessage(dismissComment);
      commentService.createComment(comment);
    }
  }

}
