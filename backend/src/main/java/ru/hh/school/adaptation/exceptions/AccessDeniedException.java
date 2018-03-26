package ru.hh.school.adaptation.exceptions;

import ru.hh.school.adaptation.dto.ErrorDto;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class AccessDeniedException extends WebApplicationException {
  public AccessDeniedException(String message) {
    super(
        message,
        Response.status(Response.Status.FORBIDDEN).entity(
            new ErrorDto(message, Response.Status.FORBIDDEN.getStatusCode())
        ).build()
    );
  }
}
