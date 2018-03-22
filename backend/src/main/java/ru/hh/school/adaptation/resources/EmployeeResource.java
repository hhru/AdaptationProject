package ru.hh.school.adaptation.resources;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.hh.school.adaptation.dto.EmployeeDto;
import ru.hh.school.adaptation.services.EmployeeService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import java.util.List;

@Path("/")
@Singleton
public class EmployeeResource {
  private final EmployeeService employeeService;

  @Inject
  public EmployeeResource(EmployeeService employeeService) {
    this.employeeService = employeeService;
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
