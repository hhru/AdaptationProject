package ru.hh.school.adaptation.resources;

import ru.hh.school.adaptation.entities.User;
import ru.hh.school.adaptation.services.auth.AuthService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import java.util.Optional;

@Path("/")
@Singleton
public class MainResource {
  private final AuthService authService;

  @Inject
  public MainResource(AuthService authService) {
    this.authService = authService;
  }

  @GET
  @Path("/index")
  public String index(@Context HttpServletRequest request) {
    String content;

    if (!authService.isUserLoggedIn(request)) {
      content = "<div><form action=\"/login\" method=\"post\"><button>Login</></form></div>";
    } else {
      Optional<User> optUser = authService.getUser(request);
      if (optUser.isPresent()) {
        User user = optUser.get();
        content = "<div>Hello, " + user.getFirstName() + " " + user.getLastName() + "</div>" +
                "<div><form action=\"/logout\" method=\"post\"><button>Logout</></form></div>";
      } else {
        content = "Internal error";
      }
    }

    return "<html><body>" + content + "</body></html>";
  }
}
