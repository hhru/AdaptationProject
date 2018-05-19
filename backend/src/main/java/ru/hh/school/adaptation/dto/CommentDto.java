package ru.hh.school.adaptation.dto;

import ru.hh.school.adaptation.entities.Comment;

public class CommentDto {
  public Integer id;
  public Integer employeeId;
  public String author;
  public String message;

  public CommentDto() {
  }

  public CommentDto(Comment comment, Integer currentUserId){
    id = comment.getId();
    employeeId = comment.getEmployee().getId();
    author = currentUserId.equals(comment.getAuthor().getId()) ? "Вы" : comment.getAuthor().getSelf().getFirstName() + " "
            + comment.getAuthor().getSelf().getLastName();
    message = comment.getMessage();
  }

}
