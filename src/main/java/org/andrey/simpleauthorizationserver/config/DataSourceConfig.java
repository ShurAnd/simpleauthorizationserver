package org.andrey.simpleauthorizationserver.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

import javax.sql.DataSource;

/**
 * Класс для конфигурации DataSource для JDBC
 */
@Configuration
@EnableJdbcRepositories(basePackages = "org.andrey.simpleauthorizationserver.*")
public class DataSourceConfig {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    @Value("${db.url}")
    private String dbUrl;
    @Value("${db.driver}")
    private String driver;
    @Value("${db.username}")
    private String username;
    @Value("${db.password}")
    private String password;

    /**
     * DataSource к postgresDB
     */
    @Bean
    public DataSource dataSource(){
        logger.info(driver + "|" + dbUrl + "|" + username + "|" + password);
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driver);
        dataSourceBuilder.url(dbUrl);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }
}
