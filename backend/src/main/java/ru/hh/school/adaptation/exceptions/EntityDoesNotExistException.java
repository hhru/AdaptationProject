package ru.hh.school.adaptation.exceptions;

import ru.hh.school.adaptation.dto.ErrorDto;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class EntityDoesNotExistException extends WebApplicationException {

  public EntityDoesNotExistException(String message){
    super(Response.status(Response.Status.BAD_REQUEST).entity(
        new ErrorDto(message)
    ).build());
  }

}
