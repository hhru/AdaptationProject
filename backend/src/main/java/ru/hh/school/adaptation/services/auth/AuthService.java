package ru.hh.school.adaptation.services.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.hh.school.adaptation.dto.HhUserInfoDto;
import ru.hh.school.adaptation.dto.UserDto;
import ru.hh.school.adaptation.entities.User;
import ru.hh.school.adaptation.exceptions.AccessDeniedException;
import ru.hh.school.adaptation.exceptions.EntityNotFoundException;
import ru.hh.school.adaptation.services.UserService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Optional;

public class AuthService {
  private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

  private HhApiService apiService;
  private UserService userService;

  @Inject
  public AuthService(HhApiService apiService, UserService userService) {
    this.apiService = apiService;
    this.userService = userService;
  }

  public URI getAuthorizationUri() {
    return URI.create(apiService.getAuthorizationUrl());
  }

  public void authorize(HttpServletRequest request) {
    String code = request.getParameter("code");
    try {
      HhUserInfoDto userInfo = apiService.getUserInfo(code);
      if (userInfo == null) {
        logger.error("Oops! Something goes wrong while get hh user info.");
        return;
      }

      User user = userService.getUserByHhid(userInfo.getId()).orElseThrow(
              () -> new AccessDeniedException(String.format("Access denied for user with id %d", userInfo.getId()))
      );

      userService.getUserDto(user.getId()).ifPresent(userDto -> {
        userDto.firstName = userInfo.getFirstName();
        userDto.lastName = userInfo.getLastName();
        userDto.middleName = userInfo.getMiddleName();
        userDto.email = userInfo.getEmail();
        userService.updateUser(userDto);

        UserSession userSession = getUserSession(request);
        userSession.setId(user.getId());
      });
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
  }

  public void logout(HttpServletRequest request) {
    if (isUserLoggedIn(request)) {
      getUserSession(request).logout();
    }
  }

  public boolean isUserLoggedIn(HttpServletRequest request) {
    return getUserSession(request).getId() != null;
  }

  public Optional<User> getUser(HttpServletRequest request) {
    Integer userId = getUserSession(request).getId();
    return userService.getUser(userId);
  }

  private UserSession getUserSession(HttpServletRequest request) {
    return new UserSession(request.getSession());
  }
}
