package ru.hh.school.adaptation;

import com.github.scribejava.apis.HHApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.hh.nab.core.util.FileSettings;
import ru.hh.nab.hibernate.MappingConfig;
import ru.hh.school.adaptation.dao.EmployeeDao;
import ru.hh.school.adaptation.services.UserService;
import ru.hh.school.adaptation.services.auth.AuthService;
import ru.hh.school.adaptation.services.auth.HhApiService;
import ru.hh.school.adaptation.dao.ExampleDao;
import ru.hh.school.adaptation.dao.MailTemplateDao;
import ru.hh.school.adaptation.dao.UserDao;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.entities.Example;
import ru.hh.school.adaptation.entities.MailTemplate;
import ru.hh.school.adaptation.entities.User;
import ru.hh.school.adaptation.resources.EmployeeResource;
import ru.hh.school.adaptation.resources.AuthResource;
import ru.hh.school.adaptation.resources.ExampleResource;
import ru.hh.school.adaptation.services.EmployeeService;

@Configuration
@Import({
        ExampleDao.class,
        MailTemplateDao.class,
        EmployeeDao.class,
        UserDao.class,
        UserService.class,
        ExampleResource.class,
        EmployeeResource.class,
        EmployeeService.class,
        HhApiService.class,
        AuthService.class,
        AuthResource.class
})
public class AdaptationCommonConfig {

  public static final String JSON_DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";

  @Bean
  MappingConfig mappingConfig() {
    return new MappingConfig(Example.class, MailTemplate.class, Employee.class, User.class);
  }

  @Bean
  OAuth20Service oauthService(FileSettings fileSettings) {
    String clientId = fileSettings.getString("oauth.client.id");
    String clientSecret = fileSettings.getString("oauth.client.secret");
    String redirectUri = fileSettings.getString("oauth.redirect-uri");
    return new ServiceBuilder(clientId)
            .apiSecret(clientSecret)
            .callback(redirectUri)
            .build(HHApi.instance());
  }
}
