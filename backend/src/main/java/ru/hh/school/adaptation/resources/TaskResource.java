package ru.hh.school.adaptation.resources;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.hh.school.adaptation.dto.TaskFormDto;
import ru.hh.school.adaptation.services.TaskService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

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
