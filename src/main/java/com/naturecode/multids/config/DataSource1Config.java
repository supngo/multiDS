package com.naturecode.multids.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.naturecode.multids.repo.datasource1", // Repositories for first DB
    entityManagerFactoryRef = "dataSource1EntityManagerFactory", transactionManagerRef = "dataSource1TransactionManager")
public class DataSource1Config {

  @Value("${spring.datasource1.url}")
  private String url;

  @Value("${spring.datasource1.username}")
  private String username;

  @Value("${spring.datasource1.password}")
  private String password;

  @Value("${spring.datasource1.driver-class-name}")
  private String driverClass;

  // @PostConstruct
  // public void init() {
  //   System.out.println("===== Database URL: " + url);
  // }

  @Primary
  @Bean(name = "dataSource1")
  // @ConfigurationProperties(prefix = "spring.datasource1")
  public DataSource dataSource() {
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setJdbcUrl(url);
    dataSource.setUsername(username);
    dataSource.setPassword(password);
    dataSource.setDriverClassName(driverClass);
    return dataSource;
  }

  // @Primary
  // @Bean(name = "dataSource1")
  // @ConfigurationProperties(prefix = "spring.datasource1")
  // public DataSource dataSource() {
  //   return DataSourceBuilder.create().build();
  // }

  @Primary
  @Bean(name = "dataSource1EntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(
      EntityManagerFactoryBuilder builder,
      @Qualifier("dataSource1") DataSource dataSource) {
    return builder
        .dataSource(dataSource)
        .packages("com.naturecode.multids.model.datasource1") // Entity package
        .persistenceUnit("datasource1")
        .build();
  }

  @Primary
  @Bean(name = "dataSource1TransactionManager")
  public PlatformTransactionManager transactionManager(
      @Qualifier("dataSource1EntityManagerFactory") EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }
}
