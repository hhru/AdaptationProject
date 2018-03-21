package ru.hh.school.adaptation.resources;

import org.springframework.web.bind.annotation.ResponseBody;

import ru.hh.school.adaptation.dto.WorkflowDto;
import ru.hh.school.adaptation.services.WorkflowService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;

@Path("/")
@Singleton
public class WorkflowResource {
  private final WorkflowService workflowService;

  @Inject
  public WorkflowResource(WorkflowService workflowService) {
    this.workflowService = workflowService;
  }

  @GET
  @Produces("application/json")
  @Path("/workflow")
  @ResponseBody
  public List<WorkflowDto> getAll() {
    return workflowService.getAllWorkflows();
  }
}
