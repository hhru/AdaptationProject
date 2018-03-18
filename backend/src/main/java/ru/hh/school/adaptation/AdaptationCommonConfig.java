package ru.hh.school.adaptation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.hh.nab.hibernate.MappingConfig;
import ru.hh.school.adaptation.dao.*;
import ru.hh.school.adaptation.entities.*;
import ru.hh.school.adaptation.resources.EmployeeResource;
import ru.hh.school.adaptation.resources.ExampleResource;
import ru.hh.school.adaptation.services.EmployeeService;

@Configuration
@Import({
        ExampleDao.class,
        MailTemplateDao.class,
        EmployeeDao.class,
        UserDao.class,
        WorkflowDao.class,
        ExampleResource.class,
        EmployeeResource.class,
        EmployeeService.class
})
public class AdaptationCommonConfig {

  public static final String JSON_DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";

  @Bean
    MappingConfig mappingConfig() {
      return new MappingConfig(Example.class, MailTemplate.class, Employee.class, User.class, Workflow.class);
    }
}
