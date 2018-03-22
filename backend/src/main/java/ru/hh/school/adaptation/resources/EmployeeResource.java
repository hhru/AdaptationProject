package ru.hh.school.adaptation.resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.hh.school.adaptation.dto.WorkflowStepDto;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.entities.WorkflowStep;
import ru.hh.school.adaptation.services.EmployeeService;
import ru.hh.school.adaptation.services.WorkflowService;

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
  public ResponseEntity<Void> setEmployeeWorkflow(@PathParam("id") Integer id, @RequestBody Integer next) {
    Employee employee = employeeService.getEmployee(id);
    WorkflowStep workflowStep = workflowService.getWorkflowStep(next);
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
}
