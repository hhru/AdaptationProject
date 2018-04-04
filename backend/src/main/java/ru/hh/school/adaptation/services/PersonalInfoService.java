package ru.hh.school.adaptation.services;

import org.springframework.transaction.annotation.Transactional;
import ru.hh.school.adaptation.dao.PersonalInfoDao;
import ru.hh.school.adaptation.dto.PersonalDto;
import ru.hh.school.adaptation.entities.PersonalInfo;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class PersonalInfoService {

  private PersonalInfoDao personalInfoDao;

  @Inject
  public PersonalInfoService(PersonalInfoDao personalInfoDao){
    this.personalInfoDao = personalInfoDao;
  }

  @Transactional(readOnly = true)
  public List<PersonalInfo> getAllPersonalInfo() {
    return personalInfoDao.getAllRecords();
  }

  @Transactional(readOnly = true)
  public List<PersonalDto> getAllPersonalDto() {
    return getAllPersonalInfo().stream().map(PersonalDto::new).collect(Collectors.toList());
  }

  public PersonalInfo getOrCreatePersonalInfo(PersonalDto personalDto){
    if (personalDto.id == null){
      return dtoToEntity(personalDto);
    } else {
      return personalInfoDao.getRecordById(personalDto.id);
    }
  }

  public PersonalInfo createPersonalInfo(PersonalDto personalDto){
    return dtoToEntity(personalDto);
  }

  public PersonalInfo updatePersonalInfo(PersonalInfo personalInfo, PersonalDto personalDto) {
    personalInfo.setInside(personalDto.inside);
    personalInfo.setEmail(personalDto.email);
    personalInfo.setLastName(personalDto.lastName);
    personalInfo.setMiddleName(personalDto.middleName);
    personalInfo.setFirstName(personalDto.firstName);
    personalInfoDao.update(personalInfo);
    return personalInfo;
  }

  private PersonalInfo dtoToEntity(PersonalDto personalDto){
    PersonalInfo personalInfo = new PersonalInfo();
    personalInfo.setFirstName(personalDto.firstName);
    personalInfo.setMiddleName(personalDto.middleName);
    personalInfo.setLastName(personalDto.lastName);
    personalInfo.setEmail(personalDto.email);
    personalInfo.setInside(personalDto.inside);
    personalInfoDao.save(personalInfo);
    return personalInfo;
  }

}
