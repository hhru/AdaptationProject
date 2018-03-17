package ru.hh.school.adaptation.resources;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.school.adaptation.dao.ExampleDao;
import ru.hh.school.adaptation.dao.MailTemplateDao;
import ru.hh.school.adaptation.services.MailService;

@Path("/")
@Singleton
public class ExampleResource {

  private final ExampleDao exampleDao;
  private final MailTemplateDao mailTemplateDao;
  private final MailService mailService;

  @Inject
  public ExampleResource(ExampleDao exampleDao, MailTemplateDao mailTemplateDao, MailService mail) {
    this.exampleDao = exampleDao;
    this.mailTemplateDao = mailTemplateDao;
    this.mailService = mail;

  @GET
  @Path("/hello")
  @Transactional
  public String hello() {
    String name = exampleDao.getRecordById(1).getName();
    return String.format("Hello, %s!", name);
  }
}
