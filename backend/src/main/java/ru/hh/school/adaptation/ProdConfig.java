package ru.hh.school.adaptation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.hh.nab.core.CoreProdConfig;
import ru.hh.nab.hibernate.DataSourceFactory;
import ru.hh.nab.hibernate.HibernateProdConfig;
import ru.hh.nab.hibernate.datasource.DataSourceType;
import ru.hh.school.adaptation.services.MailService;

import javax.sql.DataSource;

@Configuration
@Import({
        CoreProdConfig.class,
        HibernateProdConfig.class,
        MailService.class,
        AdaptationCommonConfig.class
})
public class ProdConfig {

  @Bean
  String serviceName() {
    return "adaptation";
  }

  @Bean
  DataSource dataSource(DataSourceFactory dataSourceFactory) {
    return dataSourceFactory.create(DataSourceType.DEFAULT);
  }
}
