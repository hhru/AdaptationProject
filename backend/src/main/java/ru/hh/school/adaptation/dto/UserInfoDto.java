package ru.hh.school.adaptation.dto;

import ru.hh.school.adaptation.entities.User;

import static ru.hh.school.adaptation.entities.AccessType.ADMIN;

public class UserInfoDto {
  public String firstName;

  public String lastName;

  public String middleName;

  public String email;

  public String inside;

  public boolean isAdmin;

  public UserInfoDto(User user) {
    firstName = user.getSelf().getFirstName();
    lastName = user.getSelf().getLastName();
    middleName = user.getSelf().getMiddleName();
    email = user.getSelf().getEmail();
    inside = user.getSelf().getInside();
    isAdmin = user.getAccessRule().getAccessType().equals(ADMIN);
  }
}
