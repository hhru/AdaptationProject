package ru.hh.school.adaptation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.hh.nab.core.CoreProdConfig;
import ru.hh.nab.hibernate.DataSourceFactory;
import ru.hh.nab.hibernate.HibernateProdConfig;
import ru.hh.nab.hibernate.MappingConfig;
import ru.hh.nab.hibernate.datasource.DataSourceType;
import ru.hh.school.adaptation.dao.ExampleDao;
import ru.hh.school.adaptation.entities.Example;
import ru.hh.school.adaptation.resources.ExampleResource;

import javax.sql.DataSource;

@Configuration
@Import({
        CoreProdConfig.class,
        HibernateProdConfig.class,
        ExampleDao.class,
        ExampleResource.class
})
public class ProdConfig {

    @Bean
    String serviceName() {
        return "adaptation";
    }

    @Bean
    MappingConfig mappingConfig() {
        return new MappingConfig(Example.class);
    }

    @Bean
    DataSource dataSource(DataSourceFactory dataSourceFactory) {
        return dataSourceFactory.create(DataSourceType.DEFAULT);
    }
}