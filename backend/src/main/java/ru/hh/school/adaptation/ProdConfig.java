package ru.hh.school.adaptation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;

import ru.hh.nab.core.CoreProdConfig;
import ru.hh.nab.core.util.FileSettings;
import ru.hh.nab.hibernate.DataSourceFactory;
import ru.hh.nab.hibernate.HibernateProdConfig;
import ru.hh.nab.hibernate.datasource.DataSourceType;
import ru.hh.school.adaptation.services.MailService;
import ru.hh.school.adaptation.services.MeetingService;

import javax.sql.DataSource;

@Configuration
@Import({
        CoreProdConfig.class,
        HibernateProdConfig.class,
        MailService.class,
        MeetingService.class,
        AdaptationCommonConfig.class
})
public class ProdConfig {

  @Bean
  String serviceName() {
    return "adaptation";
  }

  @Bean
  DataSource dataSource(DataSourceFactory dataSourceFactory, FileSettings settings) {
    return dataSourceFactory.create(DataSourceType.MASTER, settings);
  }

  @Bean
  ExchangeService exchangeService() {
    return new ExchangeService(ExchangeVersion.Exchange2010_SP2);
  }
}
