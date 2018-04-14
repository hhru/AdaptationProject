package ru.hh.school.adaptation.resources;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.hh.school.adaptation.dto.TaskFormDto;
import ru.hh.school.adaptation.misc.CommonUtils;
import ru.hh.school.adaptation.misc.Named;
import ru.hh.school.adaptation.services.TaskService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Singleton
public class TaskResource {
  private final TaskService taskService;

  @Inject
  public TaskResource(TaskService taskService){
    this.taskService = taskService;
  }

  @GET
  @Produces("application/json")
  @Path("/employee/tasks/{employeeId}")
  @ResponseBody
  public TaskFormDto get(@PathParam("employeeId") Integer employeeId) {
    return taskService.getTasksDtoByEmployee(employeeId);
  }


  @GET
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  @Path("/employee/tasks/{employeeId}/doc")
  @ResponseBody
  public Response getEmployeeTaskDoc(@PathParam("employeeId") Integer employeeId, @HeaderParam("user-agent") String userAgent) {
    Named<byte[]> document = taskService.generateTaskDoc(employeeId);
    return Response.ok(document.get()).header(
        "Content-Disposition", String.format(
            "attachment; filename=\"%s.docx\"",
            CommonUtils.getContentDispositionFilename(userAgent, document.name())
        )
    ).build();
  }

  @GET
  @Produces("application/json")
  @Path("/tasks/{key}")
  @ResponseBody
  public TaskFormDto get(@PathParam("key") String key) {
    return taskService.getTasksDtoByKey(key);
  }

  @POST
  @Produces("application/json")
  @Path("/tasks/submit")
  @ResponseBody
  public TaskFormDto submit(@RequestBody TaskFormDto taskFormDto){
    return taskService.submitTaskForm(taskFormDto);
  }

}
