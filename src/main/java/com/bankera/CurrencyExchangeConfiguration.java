package com.bankera;

import com.bankera.config.CurrencyExchangeProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

/**
 * Configuration class.
 */
@Configuration
@PropertySource("classpath:queries/exchange-rate-queries.xml")
@EnableConfigurationProperties({CurrencyExchangeProperties.class})
public class CurrencyExchangeConfiguration {

    @Bean
    public DataSource dataSource(@Value("${database.datasource.jdbc-url}") final String url,
                                 @Value("${database.datasource.driver-class-name}") final String driver,
                                 @Value("${database.datasource.hikari.pool-name}") final String poolName,
                                 @Value("${database.datasource.hikari.minimum-idle}") final Integer minimumIdle,
                                 @Value("${database.datasource.hikari.maximum-pool-size}") final Integer maximumPoolSize) {
        final HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setDriverClassName(driver);
        hikariConfig.setPoolName(poolName);
        hikariConfig.setMinimumIdle(minimumIdle);
        hikariConfig.setMaximumPoolSize(maximumPoolSize);
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(final DataSource datasource) {
        return new NamedParameterJdbcTemplate(datasource);
    }
}
