package ru.hh.school.adaptation.resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.hh.school.adaptation.dto.WorkflowDto;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.entities.Workflow;
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
  @Path("/employee/{id}/workflow")
  @ResponseBody
  @Transactional
  public WorkflowDto getEmployeeWorkflow(@PathParam("id") Integer id) {
    return new WorkflowDto(employeeService.getEmployee(id).getWorkflow());
  }

  @PUT
  @Produces("application/json")
  @Path("/employee/{id}/workflow")
  @ResponseBody
  public ResponseEntity<Void> setEmployeeWorkflow(@PathParam("id") Integer id, @RequestBody Integer next) {
    Employee employee = employeeService.getEmployee(id);
    Workflow workflow = workflowService.getWorkflow(next);
    employee.setWorkflow(workflow);
    employeeService.updateEmployee(employee);
    return new ResponseEntity<>(null, HttpStatus.OK);
  }

  @GET
  @Produces("application/json")
  @Path("/employee/{id}/all_workflow")
  @ResponseBody
  public List<WorkflowDto> getAllWorkflow(@PathParam("id") Integer id) {
    Employee employee = employeeService.getEmployee(id);
    return workflowService.getAllWorkflows(employee.getWorkflowSet().getId());
  }
}
