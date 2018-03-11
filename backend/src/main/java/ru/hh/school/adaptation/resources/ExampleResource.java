package ru.hh.school.adaptation.resources;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.school.adaptation.dao.ExampleDao;

@Path("/")
@Singleton
public class ExampleResource {

  private final ExampleDao exampleDao;

  @Inject
  public ExampleResource(ExampleDao exampleDao) {
    this.exampleDao = exampleDao;
  }

  @GET
  @Path("/hello")
  @Transactional
  public String hello() {
    String name = exampleDao.getRecordById(1).getName();
    return String.format("Hello, %s!", name);
  }
}
