package ru.hh.school.adaptation.dto;

public class ErrorDto {

  public String error;

  public int responseCode;

  public ErrorDto(String error, int responseCode){
    this.error = error;
    this.responseCode = responseCode;
  }

}
