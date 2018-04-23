package ru.hh.school.adaptation.resources;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.hh.school.adaptation.dto.PersonalDto;
import ru.hh.school.adaptation.entities.PersonalInfo;
import ru.hh.school.adaptation.services.PersonalInfoService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("/")
@Singleton
public class PersonalInfoResource {

  private final PersonalInfoService personalInfoService;

  @Inject
  public PersonalInfoResource(PersonalInfoService personalInfoService) {
    this.personalInfoService = personalInfoService;
  }

  @GET
  @Produces("application/json")
  @Path("/personal/all")
  @ResponseBody
  public List<PersonalDto> getAll() {
    return personalInfoService.getAllPersonalDto();
  }

  @POST
  @Produces("application/json")
  @Path("/personal/create")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public PersonalDto createPerson(@RequestBody PersonalDto personalDto) {
    PersonalInfo personalInfo = personalInfoService.createPersonalInfo(personalDto);
    return new PersonalDto(personalInfo);
  }

}
