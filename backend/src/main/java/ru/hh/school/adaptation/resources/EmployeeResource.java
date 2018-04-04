package ru.hh.school.adaptation.resources;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.hh.school.adaptation.dto.*;
import ru.hh.school.adaptation.services.EmployeeService;
import ru.hh.school.adaptation.services.TransitionService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import java.util.List;

@Path("/")
@Singleton
public class EmployeeResource {
  private final EmployeeService employeeService;
  private final TransitionService transitionService;

  @Inject
  public EmployeeResource(EmployeeService employeeService, TransitionService transitionService) {
    this.employeeService = employeeService;
    this.transitionService = transitionService;
  }

  @GET
  @Produces("application/json")
  @Path("/employee/{id}/step")
  @ResponseBody
  public TransitionDto getCurrentEmployeeTransition(@PathParam("id") Integer id) {
    return transitionService.getCurrentTransitionByEmployeeId(id);
  }

  @PUT
  @Produces("application/json")
  @Path("/employee/{id}/step/next")
  @ResponseBody
  public void setEmployeeTransition(@PathParam("id") Integer id) {
    transitionService.setEmployeeNextTransition(id);
  }

  @GET
  @Produces("application/json")
  @Path("/employee/{id}/step/all")
  @ResponseBody
  public List<TransitionDto> getAllTransition(@PathParam("id") Integer id) {
    return transitionService.getAllTransitionDtoByEmployeeId(id);
  }

  @GET
  @Produces("application/json")
  @Path("/employee/all")
  @ResponseBody
  public List<EmployeeBriefDto> getAll() {
    return employeeService.getBriefEmployeesList();
  }

  @GET
  @Produces("application/json")
  @Path("/employee/{id}")
  @ResponseBody
  public EmployeeDto getEmployee(@PathParam("id") Integer id) {
    return employeeService.getEmployeeDto(id);
  }

  @POST
  @Produces("application/json")
  @Path("/employee/create")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public EmployeeDto createEmployee(@RequestBody EmployeeCreateDto employeeCreateDto){
    return employeeService.createEmployee(employeeCreateDto);
  }

  @PUT
  @Produces("application/json")
  @Path("/employee/update")
  @ResponseBody
  public EmployeeDto updateEmployee(@RequestBody EmployeeUpdateDto employeeUpdateDto){
    return employeeService.updateEmployee(employeeUpdateDto);
  }

}
