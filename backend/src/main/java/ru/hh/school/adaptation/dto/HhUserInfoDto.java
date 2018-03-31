package ru.hh.school.adaptation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HhUserInfoDto {
  @JsonProperty("id")
  public Integer id;

  @JsonProperty("first_name")
  public String firstName;

  @JsonProperty("last_name")
  public String lastName;

  @JsonProperty("middle_name")
  public String middleName;

  @JsonProperty("email")
  public String email;
}
