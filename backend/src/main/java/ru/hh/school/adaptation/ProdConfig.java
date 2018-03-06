package ru.hh.school.adaptation;

import static java.util.Collections.singletonMap;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ru.hh.nab.core.CoreProdConfig;
import ru.hh.nab.hibernate.DataSourceFactory;
import ru.hh.nab.hibernate.HibernateProdConfig;
import ru.hh.nab.hibernate.MappingConfig;
import ru.hh.nab.hibernate.datasource.DataSourceType;
import ru.hh.nab.hibernate.datasource.RoutingDataSource;

import javax.sql.DataSource;

@Configuration
@Import({
    CoreProdConfig.class,
    HibernateProdConfig.class,
    DistributorDao.class,
    ExampleResource.class
})
public class ProdConfig {

  @Bean
  String serviceName() {
    return "adaptation";
  }

  @Bean
  MappingConfig mappingConfig() {
    return new MappingConfig(Distributor.class);
  }

  @Bean
  DataSource dataSource(DataSourceFactory dataSourceFactory) {
    return dataSourceFactory.create(DataSourceType.DEFAULT);
  }
}
