package com.cr.config;

import com.google.gson.Gson;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
public class JpaConfiguration {

    @Bean
    public DataSource dataSource(){
        AwsSecret dbCredential = getSecret();
        return  DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:"+dbCredential.getEngine()+"://"+dbCredential.getHost()+":"+dbCredential.getPort()+"/cr_dev")
                .username(dbCredential.getUsername())
                .password(dbCredential.getPassword())
                .build();

        /*return  DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/cr_dev")
                .username("root")
                .password("admin")
                .build();*/
    }


    public AwsSecret getSecret() {

        System.setProperty("aws.accessKeyId", "AKIATTC3A47FZTSTVUHN");
        System.setProperty("aws.secretAccessKey", "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");
        String secretName = "dev/Credit/MySql";
        Region region = Region.of("us-west-1");

        // Create a Secrets Manager client
        SecretsManagerClient client = SecretsManagerClient.builder()
                .region(region)
                .build();

        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

        GetSecretValueResponse getSecretValueResponse;

        try {
            getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
        } catch (Exception e) {
            throw e;
        }

        Gson gson = new Gson();
        return gson.fromJson(getSecretValueResponse.secretString(), AwsSecret.class);
        // Your code goes here.
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf){
        return new JpaTransactionManager(emf);
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter(){
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(Database.MYSQL);
        jpaVendorAdapter.setGenerateDdl(true);
        return jpaVendorAdapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean lcemf = new LocalContainerEntityManagerFactoryBean();
        lcemf.setDataSource(dataSource());
        lcemf.setJpaVendorAdapter(jpaVendorAdapter());
        lcemf.setPackagesToScan("com.cr");
        return lcemf;
    }
}
