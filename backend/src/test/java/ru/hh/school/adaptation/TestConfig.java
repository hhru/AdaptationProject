package ru.hh.school.adaptation;

import mocks.HardWorkerMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ru.hh.metrics.StatsDSender;
import ru.hh.nab.core.CoreCommonConfig;
import ru.hh.nab.core.util.FileSettings;
import ru.hh.nab.hibernate.DataSourceFactory;
import ru.hh.nab.hibernate.HibernateCommonConfig;
import ru.hh.nab.hibernate.MappingConfig;
import ru.hh.nab.hibernate.datasource.DataSourceType;
import ru.hh.nab.hibernate.datasource.RoutingDataSource;
import ru.hh.school.adaptation.hard.work.HardWorker;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import static java.util.Collections.singletonMap;

@Configuration
@Import({
        CoreCommonConfig.class,
        HibernateCommonConfig.class,
        HardWorkerMock.class,
        DistributorDao.class,
        ExampleResource.class})
public class TestConfig {

  @Bean
  MappingConfig mappingConfig() {
    return new MappingConfig(Distributor.class);
  }

  @Bean
  FileSettings fileSettings() throws IOException {
    Properties properties = new Properties();
    try (InputStream resource = getClass().getResourceAsStream("/service.properties")) {
        properties.load(resource);
    }

    return new FileSettings(properties);
  }

  @Bean(destroyMethod = "shutdown")
  static EmbeddedDatabase dataSource() {
      return createEmbeddedDatabase(DataSourceType.REPLICA);
  }

  private static EmbeddedDatabase createEmbeddedDatabase(DataSourceType dataSourceType) {
    return new EmbeddedDatabaseBuilder()
      .setName(dataSourceType.getId())
      .setType(EmbeddedDatabaseType.HSQL)
      .build();
  }
}
