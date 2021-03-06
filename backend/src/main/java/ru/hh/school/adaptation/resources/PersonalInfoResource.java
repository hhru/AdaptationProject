package ru.hh.school.adaptation.resources;

import javax.ws.rs.core.Response;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.hh.school.adaptation.dto.PersonalDto;
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
  @ResponseBody
  public Response createPerson(@RequestBody PersonalDto personalDto) {
    if (personalInfoService.isValidPersonalDto(personalDto)) {
      return Response.status(Response.Status.CREATED)
          .entity(new PersonalDto(personalInfoService.createPersonalInfo(personalDto)))
          .build();
    }
    return Response.status(Response.Status.BAD_REQUEST).build();
  }

}
