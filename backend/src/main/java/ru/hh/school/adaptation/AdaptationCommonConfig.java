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
import ru.hh.school.adaptation.dao.MailTemplateDao;
import ru.hh.school.adaptation.dao.PersonalInfoDao;
import ru.hh.school.adaptation.dao.TaskDao;
import ru.hh.school.adaptation.dao.TaskFormDao;
import ru.hh.school.adaptation.dao.TransitionDao;
import ru.hh.school.adaptation.dao.UserDao;
import ru.hh.school.adaptation.entities.Employee;
import ru.hh.school.adaptation.entities.MailTemplate;
import ru.hh.school.adaptation.entities.PersonalInfo;
import ru.hh.school.adaptation.entities.Task;
import ru.hh.school.adaptation.entities.TaskForm;
import ru.hh.school.adaptation.entities.Transition;
import ru.hh.school.adaptation.entities.User;
import ru.hh.school.adaptation.resources.EmployeeResource;
import ru.hh.school.adaptation.resources.TaskResource;
import ru.hh.school.adaptation.services.EmployeeService;
import ru.hh.school.adaptation.services.TaskService;
import ru.hh.school.adaptation.services.TransitionService;
import ru.hh.school.adaptation.resources.MainResource;
import ru.hh.school.adaptation.services.UserService;
import ru.hh.school.adaptation.services.auth.AuthService;
import ru.hh.school.adaptation.services.auth.HhApiService;
import ru.hh.school.adaptation.resources.AuthResource;
import ru.hh.school.adaptation.resources.PersonalInfoResource;
import ru.hh.school.adaptation.services.PersonalInfoService;


@Configuration
@Import({
    MailTemplateDao.class,
    EmployeeDao.class,
    UserDao.class,
    TransitionDao.class,
    TaskDao.class,
    TaskFormDao.class,
    EmployeeResource.class,
    EmployeeService.class,
    TransitionService.class,
    TaskService.class,
    UserService.class,
    HhApiService.class,
    AuthService.class,
    AuthResource.class,
    MainResource.class,
    TaskResource.class,
    PersonalInfoDao.class,
    PersonalInfoService.class,
    WorkflowService.class,

    AuthResource.class,
    MainResource.class,
    EmployeeResource.class,
    PersonalInfoResource.class
})
public class AdaptationCommonConfig {

  public static final String JSON_DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";
  public static final String JSON_DATE_FORMAT = "yyyy-MM-dd";

  @Bean
  MappingConfig mappingConfig() {
    return new MappingConfig(MailTemplate.class,
                             Employee.class,
                             User.class,
                             PersonalInfo.class,Transition.class,
        Task.class,
        TaskForm.class
    );
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
