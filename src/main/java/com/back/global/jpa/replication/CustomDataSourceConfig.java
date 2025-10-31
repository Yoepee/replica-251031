package com.back.global.jpa.replication;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class CustomDataSourceConfig {
    @Bean@ConfigurationProperties(prefix = "custom.datasource.source")
    public DataSource sourceDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean@ConfigurationProperties(prefix = "custom.datasource.replica1")
    public DataSource replica1DataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean@ConfigurationProperties(prefix = "custom.datasource.replica2")
    public DataSource replica2DataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    @DependsOn({"sourceDataSource", "replica1DataSource", "replica2DataSource"})
    public DataSource routeDataSource() {
        DataSourceRouter dataSourceRouter = new DataSourceRouter();

        DataSource sourceDataSource = sourceDataSource();
        DataSource replica1DataSource = replica1DataSource();
        DataSource replica2DataSource = replica2DataSource();

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(0, sourceDataSource);
        dataSourceMap.put(1, replica1DataSource);
        dataSourceMap.put(2, replica2DataSource);

        dataSourceRouter.setTargetDataSources(dataSourceMap);

        dataSourceRouter.setDefaultTargetDataSource(sourceDataSource);

        return dataSourceRouter;
    }

    @Bean
    @Primary
    @DependsOn("routeDataSource")
    public DataSource dataSource() {
        return new LazyConnectionDataSourceProxy(routeDataSource());
    }
}
