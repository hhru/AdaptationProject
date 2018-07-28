package ru.hh.school.adaptation.services;

import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.school.adaptation.dao.AccessRuleDao;
import ru.hh.school.adaptation.dto.AccessRuleCreateDto;
import ru.hh.school.adaptation.dto.AccessRuleDto;
import ru.hh.school.adaptation.entities.AccessRule;
import ru.hh.school.adaptation.entities.AccessType;
import ru.hh.school.adaptation.services.auth.AuthService;

import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.hh.school.adaptation.entities.AccessType.ADMIN;

@Singleton
public class AdminService {
  private AuthService authService;
  private AccessRuleDao accessRuleDao;

  public AdminService(@Lazy AuthService authService, AccessRuleDao accessRuleDao) {
    this.accessRuleDao = accessRuleDao;
    this.authService = authService;
  }

  @Transactional(readOnly = true)
  public List<AccessRuleDto> getRuleList(){
    authService.checkPermission(ADMIN);
    return accessRuleDao.getAllRecords().stream().map(AccessRuleDto::new).collect(Collectors.toList());
  }

  @Transactional
  public void updateAccessRule(Integer hhId, String accessType) {
    authService.checkPermission(ADMIN);

    AccessRule accessRule = accessRuleDao.getByHhId(hhId);
    accessRule.setAccessType(AccessType.valueOf(accessType.toUpperCase()));
    accessRuleDao.update(accessRule);
  }

  @Transactional
  public Integer createAccessRuleByDto(AccessRuleCreateDto accessDto) {
    authService.checkPermission(ADMIN);

    AccessRule accessRule = new AccessRule();
    accessRule.setAccessType(AccessType.valueOf(accessDto.accessType.toUpperCase()));
    accessRule.setHhId(accessDto.hhid);
    accessRuleDao.save(accessRule);

    return accessRule.getId();
  }

  @Transactional
  public void saveAccessRule(AccessRule accessRule) {
    accessRuleDao.save(accessRule);
  }

  @Transactional(readOnly = true)
  public Optional<AccessRule> getAccessRuleByHhId(Integer hhId) {
    return Optional.ofNullable(accessRuleDao.getByHhId(hhId));
  }

}
