package ru.hh.school.adaptation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Import;
import ru.hh.nab.hibernate.MappingConfig;
import ru.hh.school.adaptation.dao.ExampleDao;
import ru.hh.school.adaptation.dao.MailTemplateDao;
import ru.hh.school.adaptation.entities.Example;
import ru.hh.school.adaptation.entities.MailTemplate;
import ru.hh.school.adaptation.resources.ExampleResource;

@Configuration
@Import({
        ExampleDao.class,
        MailTemplateDao.class,
        ExampleResource.class
})
public class AdaptationCommonConfig {
  @Bean
    MappingConfig mappingConfig() {
      return new MappingConfig(Example.class, MailTemplate.class);
    }
}
