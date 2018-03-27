package ru.hh.school.adaptation.filters;

import ru.hh.school.adaptation.services.auth.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@PreMatching
public class AuthFilter implements ContainerRequestFilter {
  @Context
  HttpServletRequest request;

  @Override
  public void filter(ContainerRequestContext containerRequestContext) throws IOException {
    HttpSession session = request.getSession();
    AuthService.getSessionThreadLocal().set(session);
  }
}
