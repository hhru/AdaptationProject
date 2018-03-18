package ru.hh.school.adaptation.services;

import org.springframework.transaction.annotation.Transactional;
import ru.hh.school.adaptation.dao.UserDao;
import ru.hh.school.adaptation.dto.UserDto;
import ru.hh.school.adaptation.entities.User;
import ru.hh.school.adaptation.exceptions.EntityNotFoundException;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class UserService {
  UserDao userDao;

  public UserService(UserDao userDao) {
    this.userDao = userDao;
  }

  @Transactional(readOnly = true)
  public List<User> getAllUsers() {
    return userDao.getAllRecords();
  }

  @Transactional(readOnly = true)
  public UserDto getUserDto(Integer id) {
    return new UserDto(getUser(id));
  }

  @Transactional(readOnly = true)
  public User getUser(Integer id) {
    User user = userDao.getRecordById(id);
    if (user == null) {
      throw new EntityNotFoundException(String.format("User with id = %d does not exist", id));
    }
    return user;
  }

  @Transactional(readOnly = true)
  public User getUserByHhid(Integer hhid) {
    List<User> users = userDao.getAllRecords();
    for (User user : users) {
      if (user.getHhid().equals(hhid)) {
        return user;
      }
    }
    return null;
  }

  @Transactional
  public void saveUser(UserDto userDto) {
    User user = new User();
    user.setHhid(userDto.hhid);
    user.setFirstName(userDto.firstName);
    user.setLastName(userDto.lastName);
    user.setMiddleName(userDto.middleName);
    user.setEmail(userDto.email);

    userDao.save(user);
  }

  @Transactional
  public void updateUser(UserDto userDto) {
    User user = userDao.getRecordById(userDto.id);
    user.setHhid(userDto.hhid);
    user.setFirstName(userDto.firstName);
    user.setLastName(userDto.lastName);
    user.setMiddleName(userDto.middleName);
    user.setEmail(userDto.email);

    userDao.update(user);
  }
}
