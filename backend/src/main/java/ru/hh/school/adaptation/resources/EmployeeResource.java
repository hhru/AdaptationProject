package ru.hh.school.adaptation.resources;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.hh.school.adaptation.services.EmployeeService;
import ru.hh.school.adaptation.services.TransitionService;
import org.springframework.web.bind.annotation.ResponseStatus;

import ru.hh.school.adaptation.dto.EmployeeDto;
import ru.hh.school.adaptation.dto.TransitionDto;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

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
  public List<EmployeeDto> getAll() {
    return employeeService.getAllEmployeesDto();
  }

  @GET
  @Produces("application/json")
  @Path("/employee/{id}")
  @ResponseBody
  public EmployeeDto get(@PathParam("id") Integer id) {
    return employeeService.getEmployeeDto(id);
  }

  @POST
  @Produces("application/json")
  @Path("/employee/create")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public void create(@RequestBody EmployeeDto employeeDto) {
    employeeService.saveEmployee(employeeDto);
  }

  @PUT
  @Produces("application/json")
  @Path("/employee/update")
  @ResponseBody
  public void update(@RequestBody EmployeeDto employeeDto) {
    employeeService.updateEmployee(employeeDto);
  }
}
