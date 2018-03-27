package ru.hh.school.adaptation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.hh.nab.hibernate.MappingConfig;
import ru.hh.school.adaptation.resources.EmployeeResource;
import ru.hh.school.adaptation.resources.ExampleResource;
import ru.hh.school.adaptation.services.EmployeeService;
import ru.hh.school.adaptation.services.TransitionService;
import ru.hh.school.adaptation.dao.EmployeeDao;
import ru.hh.school.adaptation.dao.ExampleDao;
import ru.hh.school.adaptation.dao.MailTemplateDao;
import ru.hh.school.adaptation.dao.TransitionDao;
import ru.hh.school.adaptation.dao.UserDao;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.entities.Example;
import ru.hh.school.adaptation.entities.MailTemplate;
import ru.hh.school.adaptation.entities.Transition;
import ru.hh.school.adaptation.entities.User;
import ru.hh.school.adaptation.entities.WorkflowStep;

@Configuration
@Import({
        ExampleDao.class,
        MailTemplateDao.class,
        EmployeeDao.class,
        UserDao.class,
        TransitionDao.class,
        ExampleResource.class,
        EmployeeResource.class,
        EmployeeService.class,
        TransitionService.class
})
public class AdaptationCommonConfig {

  public static final String JSON_DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";

  @Bean
    MappingConfig mappingConfig() {
      return new MappingConfig(Example.class,
                               MailTemplate.class,
                               Employee.class,
                               User.class,
                               Transition.class,
                               WorkflowStep.class
      );
    }
}
