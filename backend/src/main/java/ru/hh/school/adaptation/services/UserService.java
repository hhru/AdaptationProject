package ru.hh.school.adaptation.services;

import org.springframework.transaction.annotation.Transactional;
import ru.hh.school.adaptation.dao.UserDao;
import ru.hh.school.adaptation.dto.UserDto;
import ru.hh.school.adaptation.entities.User;
import ru.hh.school.adaptation.exceptions.EntityNotFoundException;

import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class UserService {
  private final UserDao userDao;

  public UserService(UserDao userDao) {
    this.userDao = userDao;
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
  public Optional<User> getUserByHhid(Integer hhid) {
    return Optional.ofNullable(userDao.getRecordByHhid(hhid));
  }

  @Transactional
  public void saveUser(UserDto userDto) {
    User user = new User();
    user.setFirstName(userDto.firstName);
    user.setLastName(userDto.lastName);
    user.setMiddleName(userDto.middleName);
    user.setEmail(userDto.email);
    user.setHhid(userDto.hhid);
    userDao.save(user);
  }

  @Transactional
  public void updateUser(UserDto userDto) {
    User user = getUser(userDto.id).orElseThrow(() -> new EntityNotFoundException(String.format("User with id = %d does not exist", userDto.id)));
    user.setHhid(userDto.hhid);
    user.setFirstName(userDto.firstName);
    user.setLastName(userDto.lastName);
    user.setMiddleName(userDto.middleName);
    user.setEmail(userDto.email);
    userDao.update(user);
  }
}
