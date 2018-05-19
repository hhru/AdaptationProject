package ru.hh.school.adaptation.services.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.hh.school.adaptation.dto.HhUserInfoDto;
import ru.hh.school.adaptation.dto.UserDto;
import ru.hh.school.adaptation.entities.User;
import ru.hh.school.adaptation.misc.UserSession;
import ru.hh.school.adaptation.services.UserService;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.Optional;

public class AuthService {
  private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

  private HhApiService apiService;
  private UserService userService;

  private static ThreadLocal<UserSession> sessionThreadLocal = new ThreadLocal<>();

  public static void setHttpSession(HttpSession httpSession) {
    UserSession userSession = new UserSession(httpSession);
    sessionThreadLocal.set(userSession);
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
      Optional<HhUserInfoDto> userInfoDtoOptional = apiService.getUserInfoDto(code);
      if (!userInfoDtoOptional.isPresent()) {
        logger.error("Oops! Something goes wrong while get hh user info.");
        return;
      }

      HhUserInfoDto userInfoDto = userInfoDtoOptional.get();

      User user = userService.getUserByHhid(userInfoDto.id).orElseGet(() -> {
        UserDto userDto = new UserDto(userInfoDto);
        userService.saveUser(userDto);
        return userService.getUserByHhid(userInfoDto.id).get();
      });
      sessionThreadLocal.get().setId(user.getId());

    } catch (InterruptedException interruptedException) {
      Thread.currentThread().interrupt();
      throw new RuntimeException(interruptedException);
    } catch (Exception exception) {
      throw new RuntimeException(exception);
    }
  }

  public void logout() {
    if (isUserLoggedIn()) {
      sessionThreadLocal.get().logout();
    }
  }

  public boolean isUserLoggedIn() {
    return sessionThreadLocal.get().getId() != null;
  }

  public Optional<User> getUser() {
    try {
      Integer userId = sessionThreadLocal.get().getId();
      return userService.getUser(userId);
    } catch(Exception e) {
      return Optional.empty();
    }
  }

  public Integer getCurrentUserId() {
    return getUser().get().getId();
  }

}
