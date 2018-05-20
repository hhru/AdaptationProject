package ru.hh.school.adaptation.resources;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.hh.school.adaptation.dto.QuestionnaireDto;
import ru.hh.school.adaptation.services.QuestionnaireService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/")
@Singleton
public class QuestionnaireResource {
  private final QuestionnaireService questionnaireService;

  @Inject
  public QuestionnaireResource(QuestionnaireService questionnaireService){
    this.questionnaireService = questionnaireService;
  }

  @GET
  @Produces("application/json")
  @Path("/questionnaire/{key}")
  @ResponseBody
  public QuestionnaireDto get(@PathParam("key") String key) {
    return questionnaireService.getQuestionnairesDtoByKey(key);
  }

  @POST
  @Produces("application/json")
  @Path("/questionnaire/submit")
  @ResponseBody
  public void submit(@RequestBody QuestionnaireDto questionnaireDto){
    questionnaireService.submitQuestionnaire(questionnaireDto);
  }

  @GET
  @Produces("application/json")
  @Path("/employee/{employeeId}/questionnaire")
  @ResponseBody
  public QuestionnaireDto get(@PathParam("employeeId") Integer employeeId) {
    return questionnaireService.getQuestionnaireDtoByEmployee(employeeId);
  }

}
