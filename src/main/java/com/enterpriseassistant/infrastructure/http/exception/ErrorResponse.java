package com.enterpriseassistant.infrastructure.http.exception;

import org.springframework.http.HttpStatus;

public record ErrorResponse(String message,
                            HttpStatus status) {
}
