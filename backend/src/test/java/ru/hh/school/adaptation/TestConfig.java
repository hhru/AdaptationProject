package ru.hh.school.adaptation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import ru.hh.nab.core.CoreCommonConfig;
import ru.hh.nab.core.util.FileSettings;
import ru.hh.nab.hibernate.HibernateCommonConfig;
import ru.hh.nab.hibernate.datasource.DataSourceType;
import ru.hh.school.adaptation.services.MailService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.mockito.Mockito.mock;

@Configuration
@Import({
        CoreCommonConfig.class,
        HibernateCommonConfig.class,
        AdaptationCommonConfig.class})
public class TestConfig {

  @Bean
  MailService mailService() {
    return mock(MailService.class);
  }

  @Bean
  FileSettings fileSettings() throws IOException {
    Properties properties = new Properties();
    try (InputStream resource = getClass().getResourceAsStream("/service.properties")) {
      properties.load(resource);
    }

    return new FileSettings(properties);
  }

  @Bean
  Properties hibernateProperties(FileSettings fileSettings) {
    return fileSettings.getSubProperties("hibernate");
  }
  
  @Bean(destroyMethod = "shutdown")
  static EmbeddedDatabase dataSource() {
      return createEmbeddedDatabase(DataSourceType.MASTER);
  }

  private static EmbeddedDatabase createEmbeddedDatabase(DataSourceType dataSourceType) {
    return new EmbeddedDatabaseBuilder()
      .setName(dataSourceType.getName())
      .setType(EmbeddedDatabaseType.HSQL)
      .build();
  }
}
