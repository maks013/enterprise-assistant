package com.enterpriseassistant.product.domain;

import com.enterpriseassistant.product.exception.InvalidGtinNumberFormat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class GtinNumberValidator {

    private static final String GTIN_PATTERN = "^\\d{13}$";

    static void checkGtinPattern(String gtin){
        Pattern pattern = Pattern.compile(GTIN_PATTERN);
        Matcher matcher = pattern.matcher(gtin);

        if (!matcher.matches()) {
            throw new InvalidGtinNumberFormat();
        }
    }
}
