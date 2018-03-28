package ru.hh.school.adaptation.resources;

import ru.hh.school.adaptation.services.auth.AuthService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/")
@Singleton
public class AuthResource {
  private final AuthService authService;
  private static final URI BACK_URL = URI.create("index");

  @Inject
  public AuthResource(AuthService authService) {
    this.authService = authService;
  }

  @POST
  @Path("/login")
  public Response login() {
    URI uri = authService.isUserLoggedIn() ? BACK_URL : authService.getAuthorizationUri();
    return Response.seeOther(uri).build();
  }

  @POST
  @Path("/logout")
  public Response logout() {
    authService.logout();
    return Response.seeOther(BACK_URL).build();
  }

  @GET
  @Path("/auth")
  public Response auth(@QueryParam("code") String code, @QueryParam("error") String error) {
    // TODO: access denied.
    if (error == null) {
      authService.authorize(code);
    }
    return Response.temporaryRedirect(BACK_URL).build();
  }
}
