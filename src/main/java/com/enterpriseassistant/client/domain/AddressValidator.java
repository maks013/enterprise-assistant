package com.enterpriseassistant.client.domain;

class AddressValidator {

    private static final String POSTAL_PATTERN = "\\d{2}-\\d{3}|\\d{5}";

    public static boolean validatePostalCode(String postalCode) {
        return postalCode != null && postalCode.matches(POSTAL_PATTERN);
    }

}
