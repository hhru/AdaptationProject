package ru.hh.school.adaptation.services.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.hh.school.adaptation.dto.HhUserInfoDto;
import ru.hh.school.adaptation.dto.UserDto;
import ru.hh.school.adaptation.entities.AccessRule;
import ru.hh.school.adaptation.entities.AccessType;
import ru.hh.school.adaptation.entities.User;
import ru.hh.school.adaptation.exceptions.AccessDeniedException;
import ru.hh.school.adaptation.misc.UserSession;
import ru.hh.school.adaptation.services.AdminService;
import ru.hh.school.adaptation.services.UserService;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.Optional;

import static ru.hh.school.adaptation.entities.AccessType.OTHER;

public class AuthService {
  private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

  private AdminService adminService;
  private HhApiService apiService;
  private UserService userService;

  private static ThreadLocal<UserSession> sessionThreadLocal = new ThreadLocal<>();

  public static void setHttpSession(HttpSession httpSession) {
    UserSession userSession = new UserSession(httpSession);
    sessionThreadLocal.set(userSession);
  }

  @Inject
  public AuthService(HhApiService apiService, UserService userService, AdminService adminService) {
    this.adminService = adminService;
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

      AccessRule accessRule = adminService.getAccessRuleByHhId(userInfoDto.id).orElseGet(() -> {
        AccessRule newRule = new AccessRule();
        newRule.setAccessType(OTHER);
        newRule.setHhId(userInfoDto.id);
        adminService.saveAccessRule(newRule);
        return adminService.getAccessRuleByHhId(userInfoDto.id).get();
      });

      User user = userService.getUserByAccessRuleId(accessRule.getId()).orElseGet(() -> {
        UserDto userDto = new UserDto(userInfoDto);
        userService.newUser(userDto, accessRule);
        return userService.getUserByAccessRuleId(accessRule.getId()).get();
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

  public void checkPermission(AccessType accessType) {
    //TODO allow >= accessType
    Optional<User> user = getUser();
    if (!user.isPresent()) {
      logger.warn("Alarm anonymous detected");
      throw new AccessDeniedException("Alarm anonymous detected");
    }
    if (user.get().getAccessRule().getAccessType() != accessType) {
      logger.warn("User with id {} don't have rights", user.get().getId());
      throw new AccessDeniedException("Access denied");
    }
  }

  public Integer getCurrentUserId() {
    return getUser().get().getId();
  }

}
