package ru.hh.school.adaptation.services;

import org.springframework.transaction.annotation.Transactional;
import ru.hh.school.adaptation.dao.PersonalInfoDao;
import ru.hh.school.adaptation.dto.PersonalDto;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.entities.Log;
import ru.hh.school.adaptation.entities.PersonalInfo;
import ru.hh.school.adaptation.services.auth.AuthService;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;

public class PersonalInfoService {

  private AuthService authService;
  private CommentService commentService;
  private PersonalInfoDao personalInfoDao;

  @Inject
  public PersonalInfoService(PersonalInfoDao personalInfoDao, CommentService commentService, AuthService authService) {
    this.authService = authService;
    this.commentService = commentService;
    this.personalInfoDao = personalInfoDao;
  }

  @Transactional(readOnly = true)
  public List<PersonalInfo> getAllPersonalInfo() {
    return personalInfoDao.getAllRecords();
  }

  @Transactional(readOnly = true)
  public List<PersonalDto> getAllPersonalDto() {
    return getAllPersonalInfo().stream().map(PersonalDto::new).collect(Collectors.toList());
  }

  public PersonalInfo getOrCreatePersonalInfo(PersonalDto personalDto){
    if (personalDto.id == null){
      return dtoToEntity(personalDto);
    } else {
      return personalInfoDao.getRecordById(personalDto.id);
    }
  }

  public PersonalInfo getPersonalInfo(PersonalDto personalDto){
    if (personalDto != null && personalDto.id != null){
      return personalInfoDao.getRecordById(personalDto.id);
    } else {
      return null;
    }
  }

  @Transactional
  public PersonalInfo createPersonalInfo(PersonalDto personalDto){
    return dtoToEntity(personalDto);
  }

  public PersonalInfo updatePersonalInfo(PersonalInfo personalInfo, PersonalDto personalDto) {
    personalInfo.setInside(personalDto.inside);
    personalInfo.setSubdivision(personalDto.subdivision);
    personalInfo.setEmail(personalDto.email);
    personalInfo.setLastName(personalDto.lastName);
    personalInfo.setMiddleName(personalDto.middleName);
    personalInfo.setFirstName(personalDto.firstName);
    personalInfoDao.update(personalInfo);
    return personalInfo;
  }

  private PersonalInfo dtoToEntity(PersonalDto personalDto){
    PersonalInfo personalInfo = new PersonalInfo();
    personalInfo.setFirstName(personalDto.firstName);
    personalInfo.setMiddleName(personalDto.middleName);
    personalInfo.setLastName(personalDto.lastName);
    personalInfo.setEmail(personalDto.email);
    personalInfo.setInside(personalDto.inside);
    personalInfo.setSubdivision(personalDto.subdivision);
    personalInfoDao.save(personalInfo);
    return personalInfo;
  }

  public void logPersonalInfoUpdate(PersonalInfo fromPersonalInfo, PersonalDto toPersonalDto, Employee employee) {
    String user = authService.getUser().map(u -> u.getSelf().getFirstName() + " " + u.getSelf().getLastName()).orElse("Anonymous");
    Log log = new Log();
    log.setEmployee(employee);
    log.setAuthor(user);
    log.setEventDate(new Date());

    if (!fromPersonalInfo.getInside().equals(toPersonalDto.inside)) {
      log.setMessage("Inside был изменен с " +
              fromPersonalInfo.getInside() +
              " на " +
              toPersonalDto.inside);
      commentService.createLog(log);
    }

    if (!fromPersonalInfo.getSubdivision().equals(toPersonalDto.subdivision)) {
      log.setMessage("Подразделение было изменено с " +
              fromPersonalInfo.getSubdivision() +
              " на " +
              toPersonalDto.subdivision);
      commentService.createLog(log);
    }

    if (!fromPersonalInfo.getEmail().equals(toPersonalDto.email)) {
      log.setMessage("Email был изменен с " +
              fromPersonalInfo.getEmail() +
              " на " +
              toPersonalDto.email);
      commentService.createLog(log);
    }

    if (!fromPersonalInfo.getLastName().equals(toPersonalDto.lastName)) {
      log.setMessage("Фамилия была изменена с " +
              fromPersonalInfo.getLastName() +
              " на " +
              toPersonalDto.lastName);
      commentService.createLog(log);
    }

    if (!fromPersonalInfo.getMiddleName().equals(toPersonalDto.middleName)) {
      log.setMessage("Отчество было изменено с " +
              fromPersonalInfo.getMiddleName() +
              " на " +
              toPersonalDto.middleName);
      commentService.createLog(log);
    }

    if (!fromPersonalInfo.getFirstName().equals(toPersonalDto.firstName)) {
      log.setMessage("Имя было изменено с " +
              fromPersonalInfo.getFirstName() +
              " на " +
              toPersonalDto.firstName);
      commentService.createLog(log);
    }
  }

}
