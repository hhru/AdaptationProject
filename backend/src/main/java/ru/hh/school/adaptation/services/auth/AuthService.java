package ru.hh.school.adaptation.services.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.hh.school.adaptation.dto.HhUserInfoDto;
import ru.hh.school.adaptation.entities.User;
import ru.hh.school.adaptation.exceptions.AccessDeniedException;
import ru.hh.school.adaptation.services.UserService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.Optional;

public class AuthService {
  private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

  private HhApiService apiService;
  private UserService userService;

  static private ThreadLocal sessionThreadLocal = new ThreadLocal<HttpSession>();

  static public ThreadLocal<HttpSession> getSessionThreadLocal() {
    return sessionThreadLocal;
  }

  @Inject
  public AuthService(HhApiService apiService, UserService userService) {
    this.apiService = apiService;
    this.userService = userService;
  }

  public URI getAuthorizationUri() {
    return URI.create(apiService.getAuthorizationUrl());
  }

  public void authorize(String code) {
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

        UserSession userSession = getUserSession();
        userSession.setId(user.getId());
      });
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
  }

  public void logout() {
    if (isUserLoggedIn()) {
      getUserSession().logout();
    }
  }

  public boolean isUserLoggedIn() {
    return getUserSession().getId() != null;
  }

  public Optional<User> getUser() {
    Integer userId = getUserSession().getId();
    return userService.getUser(userId);
  }

  private UserSession getUserSession() {
    HttpSession session = getSessionThreadLocal().get();
    return new UserSession(session);
  }
}
