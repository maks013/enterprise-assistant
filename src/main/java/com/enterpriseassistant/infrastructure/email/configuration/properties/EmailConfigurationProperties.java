package com.enterpriseassistant.infrastructure.email.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "email.sender")
public record EmailConfigurationProperties(String from,
                                           String subject) {
}
