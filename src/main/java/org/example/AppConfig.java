package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "org.example")
@EnableJpaRepositories(basePackages = "org.example.repositories")
@Profile("default")
public class AppConfig {
    @Bean
    public DataSource dataSource() {
        String propertiesPath = "/config.properties";
        try (InputStream inputStream = AppConfig.class.getClassLoader()
                                                      .getResourceAsStream(propertiesPath)
        ) {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();

            Properties props = new Properties();
            props.load(inputStream);
            String dbUser = props.getProperty("dbUser");
            String dbPassword = props.getProperty("dbPassword");
            String jdbcUrl = props.getProperty("jdbcUrl");
            String driverClassName = props.getProperty("driverClassName");

            dataSource.setDriverClassName(driverClassName);
            dataSource.setUrl(jdbcUrl);
            dataSource.setUsername(dbUser);
            dataSource.setPassword(dbPassword);

            return dataSource;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("org.example.entities");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(additionalProperties());
        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.use_sql_comments", "true");
        return properties;
    }

}
