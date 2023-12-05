package com.enterpriseassistant.client.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class AddressDto {

    private final String postalCode;
    private final String city;
    private final String street;

}
