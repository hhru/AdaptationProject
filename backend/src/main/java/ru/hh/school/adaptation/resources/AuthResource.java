package ru.hh.school.adaptation.resources;

import ru.hh.school.adaptation.services.auth.AuthService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/")
@Singleton
public class AuthResource {
  private final AuthService authService;

  @Inject
  public AuthResource(AuthService authService) {
    this.authService = authService;
  }

  @POST
  @Path("/login")
  public Response login(@Context HttpServletRequest request) {
    URI uri = authService.isUserLoggedIn(request)
            ? URI.create("index") : authService.getAuthorizationUri();
    return Response.seeOther(uri).build();
  }

  @POST
  @Path("/logout")
  public Response logout(@Context HttpServletRequest request) {
    authService.logout(request);

    URI uri = URI.create("index");
    return Response.seeOther(uri).build();
  }

  @GET
  @Path("/auth")
  public Response auth(@Context HttpServletRequest request) {
    // TODO: access denied.
    if (request.getParameter("error") == null) {
      authService.authorize(request);
    }

    URI uri = URI.create("index");
    return Response.temporaryRedirect(uri).build();
  }
}
