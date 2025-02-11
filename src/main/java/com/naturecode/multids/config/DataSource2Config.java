package com.naturecode.multids.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
  basePackages = "com.naturecode.multids.repo.datasource2", // Repositories for second DB
  entityManagerFactoryRef = "dataSource2EntityManagerFactory", 
  transactionManagerRef = "dataSource2TransactionManager")
public class DataSource2Config {

  @Value("${spring.datasource2.url}")
  private String url;

  @Value("${spring.datasource2.username}")
  private String username;

  @Value("${spring.datasource2.password}")
  private String password;

  @Value("${spring.datasource2.driver-class-name}")
  private String driverClass;

  @Bean(name = "dataSource2")
  // @ConfigurationProperties(prefix = "spring.datasource2")
  public DataSource dataSource() {
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setJdbcUrl(url);
    dataSource.setUsername(username);
    dataSource.setPassword(password);
    dataSource.setDriverClassName(driverClass);
    return dataSource;
  }

  // @Bean(name = "dataSource2")
  // @ConfigurationProperties(prefix = "spring.datasource2")
  // public DataSource dataSource() {
  //   return DataSourceBuilder.create().build();
  // }

  @Bean(name = "dataSource2EntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(
      EntityManagerFactoryBuilder builder,
      @Qualifier("dataSource2") DataSource dataSource) {
    return builder
        .dataSource(dataSource)
        .packages("com.naturecode.multids.model.datasource2") // Entity package
        .persistenceUnit("datasource2")
        .build();
  }

  @Bean(name = "dataSource2TransactionManager")
  public PlatformTransactionManager transactionManager(
      @Qualifier("dataSource2EntityManagerFactory") EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }
}
