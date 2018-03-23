package ru.hh.school.adaptation.resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.hh.school.adaptation.dto.WorkflowStepDto;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.entities.WorkflowStep;
import ru.hh.school.adaptation.services.EmployeeService;
import ru.hh.school.adaptation.services.WorkflowService;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.hh.school.adaptation.dto.EmployeeDto;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import java.util.List;

@Path("/")
@Singleton
public class EmployeeResource {
  private final EmployeeService employeeService;
  private final WorkflowService workflowService;

  @Inject
  public EmployeeResource(EmployeeService employeeService, WorkflowService workflowService) {
    this.employeeService = employeeService;
    this.workflowService = workflowService;
  }

  @GET
  @Produces("application/json")
  @Path("/employee/{id}/step")
  @ResponseBody
  @Transactional
  public WorkflowStepDto getEmployeeWorkflow(@PathParam("id") Integer id) {
    return new WorkflowStepDto(employeeService.getEmployee(id).getWorkflowStep());
  }

  @PUT
  @Produces("application/json")
  @Path("/employee/{id}/step")
  @ResponseBody
  public ResponseEntity<Void> setEmployeeWorkflow(@PathParam("id") Integer id, @RequestBody Integer to) {
    Employee employee = employeeService.getEmployee(id);
    WorkflowStep workflowStep = workflowService.getWorkflowStep(to);
    employee.setWorkflowStep(workflowStep);
    employeeService.updateEmployee(employee);
    return new ResponseEntity<>(null, HttpStatus.OK);
  }

  @GET
  @Produces("application/json")
  @Path("/employee/{id}/step/all")
  @ResponseBody
  public List<WorkflowStepDto> getAllWorkflow(@PathParam("id") Integer id) {
    Employee employee = employeeService.getEmployee(id);
    return workflowService.getAllWorkflowSteps(employee.getWorkflow().getId());
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
