package ru.hh.school.adaptation.dto;

import ru.hh.school.adaptation.entities.PersonalInfo;
import ru.hh.school.adaptation.entities.User;

public class PersonalDto {

  public Integer id;

  public String firstName;

  public String lastName;

  public String middleName;

  public String email;

  public String inside;

  public String subdivision;

  public PersonalDto(){

  }

  public PersonalDto(PersonalInfo personalInfo){
    id = personalInfo.getId();
    firstName = personalInfo.getFirstName();
    lastName = personalInfo.getLastName();
    middleName = personalInfo.getMiddleName();
    email = personalInfo.getEmail();
    inside = personalInfo.getInside();
    subdivision = personalInfo.getSubdivision();
  }

  public PersonalDto(User user){
    id = user.getId();
    firstName = user.getSelf().getFirstName();
    lastName = user.getSelf().getLastName();
    middleName = user.getSelf().getMiddleName();
    email = user.getSelf().getEmail();
    inside = user.getSelf().getInside();
    subdivision = user.getSelf().getSubdivision();
  }

}
