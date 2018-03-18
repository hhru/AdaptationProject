package ru.hh.school.adaptation.resources;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.hh.school.adaptation.dao.EmployeeDao;
import ru.hh.school.adaptation.dto.EmployeeDto;
import ru.hh.school.adaptation.entities.Employee;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import java.util.List;
import java.util.stream.Collectors;

@Path("/")
@Singleton
public class EmployeeResource {
  private final EmployeeDao employeeDao;

  @Inject
  public EmployeeResource(EmployeeDao employeeDao) {
    this.employeeDao = employeeDao;
  }

  @GET
  @Produces("application/json")
  @Path("/employee")
  @Transactional
  public @ResponseBody
  List<EmployeeDto> getAll() {
    List<Employee> employeeList = employeeDao.getAllRecords();
    return employeeList.stream().map(EmployeeDto::new).collect(Collectors.toList());
  }

  @GET
  @Produces("application/json")
  @Path("/employee/{id}")
  @Transactional
  public @ResponseBody
  EmployeeDto get(@PathParam("id") String id) {
    return new EmployeeDto(employeeDao.getRecordById(Integer.valueOf(id)));
  }

  @PUT
  @Produces("application/json")
  @Path("/employee")
  @ResponseStatus(HttpStatus.CREATED)
  @Transactional
  public @ResponseBody
  void create(@RequestBody EmployeeDto employeeDto) {
    employeeDao.save(employeeDto);
  }

  @POST
  @Produces("application/json")
  @Path("/employee")
  @Transactional
  public @ResponseBody
  void update(@RequestBody EmployeeDto employeeDto) {
    employeeDao.update(employeeDto);
  }
}
