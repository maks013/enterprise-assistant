package com.enterpriseassistant;

import com.enterpriseassistant.infrastructure.security.jwt.properties.JwtConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@EnableConfigurationProperties(JwtConfigurationProperties.class)
@SpringBootApplication
public class EnterpriseAssistantApplication {
    public static void main(String[] args) {
        SpringApplication.run(EnterpriseAssistantApplication.class, args);
    }

    @Bean
    Validator validator() {
        return new LocalValidatorFactoryBean();
    }
}
