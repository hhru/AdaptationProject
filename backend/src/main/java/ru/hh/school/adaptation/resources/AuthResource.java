package ru.hh.school.adaptation.resources;

import org.springframework.web.bind.annotation.ResponseBody;
import ru.hh.school.adaptation.dto.UserInfoDto;
import ru.hh.school.adaptation.entities.User;
import ru.hh.school.adaptation.exceptions.AccessDeniedException;
import ru.hh.school.adaptation.services.auth.AuthService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;

import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/")
@Singleton
public class AuthResource {
  private final AuthService authService;
  private static final URI BACK_URL = URI.create("/");

  @Inject
  public AuthResource(AuthService authService) {
    this.authService = authService;
  }

  @POST
  @Path("/login")
  public Response login() {
    URI uri = authService.isUserLoggedIn() ? BACK_URL : authService.getAuthorizationUri();
    return Response.status(Response.Status.FOUND).location(uri).build();
  }

  @POST
  @Path("/logout")
  public Response logout() {
    authService.logout();
    return Response.status(Response.Status.FOUND).location(BACK_URL).build();
  }

  @GET
  @Path("/auth")
  public Response auth(@QueryParam("code") String code, @QueryParam("error") String error) {
    // TODO: access denied.
    if (error == null) {
      authService.authorize(code);
    }
    return Response.status(Response.Status.FOUND).location(BACK_URL).build();
  }

  @GET
  @Produces("application/json")
  @Path("/user")
  @ResponseBody
  public UserInfoDto getUser() {
    User user = authService.getUser().orElseThrow(() -> new AccessDeniedException("The user is not logged in."));
    return new UserInfoDto(user);
  }
}
