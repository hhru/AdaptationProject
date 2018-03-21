package ru.hh.school.adaptation.exceptions;

import ru.hh.school.adaptation.dto.ErrorDto;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class EntityNotFoundException extends WebApplicationException {

  private static final Response.Status responseStatus = Response.Status.BAD_REQUEST;

  public EntityNotFoundException(String message, Throwable cause) {
    super(
        message,
        cause,
        Response.status(responseStatus).entity(
          new ErrorDto(message, responseStatus.getStatusCode())
        ).build()
    );
  }

  public EntityNotFoundException(String message){
    super(
        message,
        Response.status(responseStatus).entity(
          new ErrorDto(message, responseStatus.getStatusCode())
        ).build()
    );
  }

  public EntityNotFoundException(Throwable cause){
    super(
        cause,
        Response.status(responseStatus).entity(
          new ErrorDto(cause.getMessage(), responseStatus.getStatusCode())
        ).build()
    );
  }

}
