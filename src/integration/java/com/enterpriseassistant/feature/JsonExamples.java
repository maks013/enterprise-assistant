package com.enterpriseassistant.feature;

public interface JsonExamples {

    default String adminRegisteringData() {
        return """
                {
                    "username": "admin",
                    "password": "admin",
                    "fullName": "Admin Admin",
                    "email": "admin@admin.com"
                }
                """;
    }

    default String adminLoginData() {
        return """
                {
                    "username": "admin",
                    "password": "admin"
                }
                """;
    }

    default String userRegistersData() {
        return """
                {
                    "username": "user",
                    "password": "password",
                    "fullName": "Example Example",
                    "email": "email@email.com"
                }
                """;
    }

    default String userLoginData() {
        return """
                {
                    "username": "user",
                    "password": "password"
                }
                """;
    }

    default String clientDataExample() {
        return """
                {
                    "taxIdNumber": "9762583826",
                    "companyName": "Example Company",
                    "address": {
                        "postalCode": "12345",
                        "city": "Example City",
                        "street": "Example Street 123"
                    },
                    "representative": {
                        "name": "John",
                        "surname": "Doe",
                        "phoneNumber": "123-456-789",
                        "email": "john.doe@example.com"
                    }
                }
                """;
    }

    default String productDataExample() {
        return """
                {
                    "gtin": "1234567890123",
                    "name": "Example Product",
                    "priceNet": "25.99",
                    "imageUrl": "https://example.com/product-image.jpg",
                    "additionalInformation": "Additional details about the product"
                }
                """;
    }

    default String serviceDataExample() {
        return """
                {
                    "name": "Example",
                    "priceNet": "100.0",
                    "imageUrl": "https://t3.ftcdn.net/jpg/00/92/53/56/360_F_92535664_IvFsQeHjBzfE6sD4VHdO8u5OHUSc6yHF.jpg",
                    "additionalInformation": "Additional"
                }
                """;
    }

    default String orderWithoutItems() {
        return """
                {
                    "clientId": 1,
                    "productOrderItems": [
                               
                    ],
                    "serviceOrderItems": [
                    
                    ],
                    "deadline": "2024-01-28T12:00:00",
                    "payment": "TRANSFER",
                    "daysToPay": "SEVEN",
                    "additionalInformation": "Additional Example Information"
                }
                """;
    }

    default String orderExample() {
        return """
                {
                    "clientId": 1,
                    "productOrderItems": [
                        {
                        "quantity": 5,
                        "productId": 1
                        }
                    ],
                    "serviceOrderItems": [
                        {
                        "quantity": 3,
                        "serviceId": 1
                        }
                    ],
                    "deadline": "2024-01-28T12:00:00",
                    "payment": "TRANSFER",
                    "daysToPay": "SEVEN",
                    "additionalInformation": "Additional Example Information"
                }
                """;
    }

    default String badCredentials() {
        return """
                {
                    "message": "Bad Credentials",
                    "status": "UNAUTHORIZED"
                }
                """;
    }

    default String accountNotActive() {
        return """
                {
                    "message": "Account is not active",
                    "status": "UNAUTHORIZED"
                }
                """;
    }
}
