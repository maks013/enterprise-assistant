package com.enterpriseassistant.client.domain;

import java.util.stream.IntStream;

class TaxIdNumberValidator {

    private static final String NIP_PATTERN = "\\d{10}";
    private static final int NIP_LENGTH = 10;
    private static final int MIN_VAL = 0;
    private static final int MAX_VAL = 9;
    private static final int CHECKSUM_DIVIDER = 11;
    private static final int[] WEIGHTS = {6, 5, 7, 2, 3, 4, 5, 6, 7};

    public static boolean validateNIP(String nip) {
        if (nip == null || nip.length() != NIP_LENGTH || !nip.matches(NIP_PATTERN)) {
            return false;
        }

        int controlSum = IntStream.range(MIN_VAL, MAX_VAL)
                .map(i -> Character.getNumericValue(nip.charAt(i)) * WEIGHTS[i])
                .sum();

        int controlNumber = controlSum % CHECKSUM_DIVIDER;
        int lastDigit = Character.getNumericValue(nip.charAt(MAX_VAL));

        return controlNumber == lastDigit;
    }
}
