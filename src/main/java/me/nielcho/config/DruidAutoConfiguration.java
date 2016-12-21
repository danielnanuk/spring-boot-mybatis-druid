package me.nielcho.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@ConditionalOnClass(DruidDataSource.class)
@ConditionalOnProperty(name = "spring.datasource.type", havingValue = "com.alibaba.druid.pool.DruidDataSource", matchIfMissing = true)
@EnableConfigurationProperties({DataSourceProperties.class, DruidAutoConfiguration.DruidProperties.class})
public class DruidAutoConfiguration {

    @Bean
    public DataSource dataSource(DataSourceProperties dataSourceProperties, DruidProperties druidProperties) {
        DataSource dataSource = dataSourceProperties.initializeDataSourceBuilder().build();
        DruidDataSource druidDataSource = (DruidDataSource) dataSource;
        Properties properties = new Properties();
        druidProperties.entrySet().forEach(e -> properties.put("druid." + e.getKey(), e.getValue()));
        druidDataSource.configFromPropety(properties);
        return druidDataSource;
    }

    @ConfigurationProperties("spring.datasource.druid")
    public static class DruidProperties extends Properties {

    }

}
