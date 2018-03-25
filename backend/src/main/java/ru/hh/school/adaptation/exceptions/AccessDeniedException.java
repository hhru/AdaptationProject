package ru.hh.school.adaptation.exceptions;

import ru.hh.school.adaptation.dto.ErrorDto;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class AccessDeniedException  extends WebApplicationException {

  private static final Response.Status responseStatus = Response.Status.BAD_REQUEST;

  public AccessDeniedException(String message){
    super(
        message,
        Response.status(responseStatus).entity(
            new ErrorDto(message, responseStatus.getStatusCode())
        ).build()
    );
  }
}
