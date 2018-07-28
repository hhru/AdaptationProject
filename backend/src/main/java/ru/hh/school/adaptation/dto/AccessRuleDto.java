package ru.hh.school.adaptation.dto;

import ru.hh.school.adaptation.entities.AccessType;
import ru.hh.school.adaptation.entities.AccessRule;

public class AccessRuleDto {
  public Integer id;
  public Integer hhid;
  public AccessType accessType;
  public String firstName;
  public String lastName;
  public String email;


  public AccessRuleDto() {
  }

  public AccessRuleDto(AccessRule accessRule) {
    id = accessRule.getId();
    hhid = accessRule.getHhId();
    accessType = accessRule.getAccessType();
    if (accessRule.getUser() != null) {
      firstName = accessRule.getUser().getSelf().getFirstName();
      lastName = accessRule.getUser().getSelf().getLastName();
      email = accessRule.getUser().getSelf().getEmail();
    }
  }
}
