package ru.hh.school.adaptation;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.school.adaptation.hard.work.HardWorker;

@Path("/")
@Singleton
public class ExampleResource {

  private final DistributorDao distributorDao;
  private final HardWorker worker;

  @Inject
  public ExampleResource(DistributorDao distributorDao, HardWorker worker) {
    this.distributorDao = distributorDao;
    this.worker = worker;
  }

  @GET
  @Path("/hello")
  @Transactional
  public String hello() {
    String name = distributorDao.getDistributorById(1).getName();
    return String.format("Hello, %s!", name);
  }

  @GET
  @Path("/hardWork")
  @Transactional
  public void doHardWork() throws InterruptedException {
    worker.doWork();
  }

  public String blah() {
    return "Blah";
  }
}
