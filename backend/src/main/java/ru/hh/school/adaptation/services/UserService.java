package ru.hh.school.adaptation.services;

import org.springframework.transaction.annotation.Transactional;
import ru.hh.school.adaptation.dao.PersonalInfoDao;
import ru.hh.school.adaptation.dao.AccessRuleDao;
import ru.hh.school.adaptation.dao.UserDao;
import ru.hh.school.adaptation.dto.UserDto;
import ru.hh.school.adaptation.entities.AccessRule;
import ru.hh.school.adaptation.entities.PersonalInfo;
import ru.hh.school.adaptation.entities.User;
import ru.hh.school.adaptation.exceptions.EntityNotFoundException;

import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Singleton
public class UserService {

  private final AccessRuleDao accessRuleDao;
  private final UserDao userDao;
  private final PersonalInfoDao personalInfoDao;

  public UserService(AccessRuleDao accessRuleDao, UserDao userDao, PersonalInfoDao personalInfoDao) {
    this.accessRuleDao = accessRuleDao;
    this.userDao = userDao;
    this.personalInfoDao = personalInfoDao;
  }

  @Transactional(readOnly = true)
  public Optional<UserDto> getUserDto(Integer id) {
    return getUser(id).map(UserDto::new);
  }

  @Transactional(readOnly = true)
  public Optional<User> getUser(Integer id) {
    return Optional.ofNullable(userDao.getRecordById(id));
  }

  @Transactional(readOnly = true)
  public Optional<User> getUserByAccessRuleId(Integer accessRuleId) {
    return Optional.ofNullable(userDao.getRecordByAccessRuleId(accessRuleId));
  }

  @Transactional(readOnly = true)
  public List<User> getAllUsers() {
    return userDao.getAllRecords();
  }

  @Transactional(readOnly = true)
  public List<UserDto> getAllUsersDto() {
    return getAllUsers().stream().map(UserDto::new).collect(Collectors.toList());
  }

  @Transactional
  public void newUser(UserDto userDto, AccessRule accessRule) {
    User user = new User();
    PersonalInfo personalInfo = new PersonalInfo();
    personalInfo.setFirstName(userDto.firstName);
    personalInfo.setLastName(userDto.lastName);
    personalInfo.setMiddleName(userDto.middleName);
    personalInfo.setEmail(userDto.email);
    personalInfoDao.save(personalInfo);
    user.setAccessRule(accessRule);
    user.setSelf(personalInfo);
    userDao.save(user);
  }

  @Transactional
  public void updateUser(UserDto userDto) {
    User user = getUser(userDto.id).orElseThrow(() -> new EntityNotFoundException(String.format("User with id = %d does not exist", userDto.id)));
    user.setAccessRule(accessRuleDao.getByHhId(userDto.hhid));
    PersonalInfo personalInfo = user.getSelf();
    personalInfo.setFirstName(userDto.firstName);
    personalInfo.setLastName(userDto.lastName);
    personalInfo.setMiddleName(userDto.middleName);
    personalInfo.setEmail(userDto.email);
    personalInfoDao.update(personalInfo);
    userDao.update(user);
  }
}
