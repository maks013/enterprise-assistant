package com.enterpriseassistant.client.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateAddressDto {

    private final String postalCode;
    private final String city;
    private final String street;

}
