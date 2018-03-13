package ru.hh.school.adaptation.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.hh.school.adaptation.dao.EmployeeDao;
import ru.hh.school.adaptation.entities.Employee;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import java.util.List;

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
    public @ResponseBody ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.ok(employeeDao.getAllRecords());
    }

    @GET
    @Produces("application/json")
    @Path("/employee/{id}")
    @Transactional
    public @ResponseBody ResponseEntity<Employee> get(@PathParam("id") String id) {
        return ResponseEntity.ok(employeeDao.getRecordById(Integer.valueOf(id)));
    }

    @PUT
    @Produces("application/json")
    @Path("/employee")
    @Transactional
    public @ResponseBody ResponseEntity<Employee> create(@RequestBody Employee employee) {
        employeeDao.save(employee);
        return ResponseEntity.ok(employee);
    }

    @POST
    @Produces("application/json")
    @Path("/employee")
    @Transactional
    public @ResponseBody ResponseEntity<Employee> update(@RequestBody Employee employee) {
        employeeDao.update(employee);
        return ResponseEntity.ok(employee);
    }
}
