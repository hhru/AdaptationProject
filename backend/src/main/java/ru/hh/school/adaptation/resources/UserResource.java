package ru.hh.school.adaptation.resources;

import org.springframework.web.bind.annotation.ResponseBody;
import ru.hh.school.adaptation.dto.UserDto;
import ru.hh.school.adaptation.services.UserService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("/")
@Singleton
public class UserResource {

  private final UserService userService;

  @Inject
  public UserResource(UserService userService) {
    this.userService = userService;
  }

  @GET
  @Produces("application/json")
  @Path("/users/all")
  @ResponseBody
  public List<UserDto> getAllUsers() {
    return userService.getAllUsersDto();
  }

}
