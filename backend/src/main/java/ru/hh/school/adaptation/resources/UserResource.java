package ru.hh.school.adaptation.resources;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.hh.school.adaptation.entities.User;
import ru.hh.school.adaptation.exceptions.AccessDeniedException;
import ru.hh.school.adaptation.services.UserService;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import ru.hh.school.adaptation.services.auth.AuthService;

@Path("/")
@Singleton
public class UserResource {

  private final UserService userService;
  private final AuthService authService;

  @Inject
  public UserResource(UserService userService, AuthService authService) {
    this.userService = userService;
    this.authService = authService;
  }

  @GET
  @Produces("application/json")
  @Path("/users/all")
  @ResponseBody
  public Map<String, Object> getAllUsers() {
    User user = authService.getUser().orElseThrow(() -> new AccessDeniedException("The user is not logged in."));
    Map<String, Object> response = new HashMap<>();
    response.put("users", userService.getAllUsersDto());
    response.put("hrId", user.getId());
    return response;
  }

}
