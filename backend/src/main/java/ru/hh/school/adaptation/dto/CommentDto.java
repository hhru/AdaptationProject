package ru.hh.school.adaptation.dto;

import ru.hh.school.adaptation.entities.Comment;

public class CommentDto {
  public Integer id;
  public Integer employeeId;
  public String author;
  public String message;

  public CommentDto(Comment comment){
    id = comment.getId();
    employeeId = comment.getEmployee().getId();
    author = comment.getAuthor();
    message = comment.getMessage();
  }

}
