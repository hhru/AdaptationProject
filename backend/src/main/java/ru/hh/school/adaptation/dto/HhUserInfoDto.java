package ru.hh.school.adaptation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HhUserInfoDto {
  @JsonProperty("id")
  private Integer id;

  @JsonProperty("first_name")
  private String firstName;

  @JsonProperty("last_name")
  private String lastName;

  @JsonProperty("middle_name")
  private String middleName;

  @JsonProperty("email")
  private String email;

  public HhUserInfoDto() {
  }

  public Integer getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public String getEmail() {
    return email;
  }
}
