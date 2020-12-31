package by.epamtraining.airlines;

import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DBInitializer {

    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource);
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScript(new ClassPathResource("db_init.sql"));
        dataSourceInitializer.setDatabasePopulator(databasePopulator);
       // dataSourceInitializer.setEnabled(Boolean.parseBoolean(initDatabase));
        dataSourceInitializer.afterPropertiesSet();
        return dataSourceInitializer;
    }
}
