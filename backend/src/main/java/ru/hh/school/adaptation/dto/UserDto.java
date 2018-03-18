package ru.hh.school.adaptation.dto;

import ru.hh.school.adaptation.entities.User;

public class UserDto {
  public Integer id;

  public Integer hhid;

  public String firstName;

  public String lastName;

  public String middleName;

  public String email;

  public UserDto() {
  }

  public UserDto(User user) {
    id = user.getId();
    hhid = user.getHhid();
    firstName = user.getFirstName();
    lastName = user.getLastName();
    middleName = user.getMiddleName();
    email = user.getEmail();
  }
}
