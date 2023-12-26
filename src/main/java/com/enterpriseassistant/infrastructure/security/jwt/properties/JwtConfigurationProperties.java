package com.enterpriseassistant.infrastructure.security.jwt.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "auth.jwt")
public record JwtConfigurationProperties(
        String secret,
        long expirationDays,
        String issuer
) {
}
