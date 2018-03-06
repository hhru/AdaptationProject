package ru.hh.school.adaptation;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.springframework.transaction.annotation.Transactional;

@Path("/")
@Singleton
public class ExampleResource {

  private final DistributorDao distributorDao;

  @Inject
  public ExampleResource(DistributorDao distributorDao) {
    this.distributorDao = distributorDao;
  }

  @GET
  @Path("/hello")
  @Transactional
  public String hello() {
    String name = distributorDao.getDistributorById(1).getName();
    return String.format("Hello, %s!", name);
  }
}
