package com.freshproduceapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import javax.sql.DataSource;
import java.sql.DriverManager;

@Configuration
public class PostgresDBConfig {

    @Value("${database.url}")
    private String url;

    @Value("${database.user}")
    private String userArn;

    @Value("${database.passwd}")
    private String passwordArn;

    @Autowired
    private SecretsManagerClient secretsManagerClient;

    @Bean
    public DataSource postgresDataSource() {
        String dbUser = getSecret(userArn);
        String dbPassword = getSecret(passwordArn);

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(dbUser);
        dataSource.setPassword(dbPassword);
        return dataSource;
    }

    private String getSecret(String secretId) {
        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder().secretId(secretId).build();
        GetSecretValueResponse getSecretValueResponse = secretsManagerClient.getSecretValue(getSecretValueRequest);
        return getSecretValueResponse.secretString();
    }


}
