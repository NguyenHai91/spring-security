package com.hainguyen.security.config;


import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class FlywayConfig {
    @Value("${spring.flyway.locations}")
    private String[] flywayLocations;

    @Value("${spring.datasource.url}")
    private String url;


    @Bean
    public DataSource datasource() {
        DriverManagerDataSource datasource = new DriverManagerDataSource();
        datasource.setUrl(url);
        return datasource;
    }

    @Bean
    public Flyway flyway() {
        Flyway flyway = Flyway.configure()
                        .dataSource(datasource())
                        .locations(flywayLocations)
                        .baselineOnMigrate(true)
                        .baselineVersion("0")
                        .load();
        flyway.migrate();
        return flyway;
    }
}
