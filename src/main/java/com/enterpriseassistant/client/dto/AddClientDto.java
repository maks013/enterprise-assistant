package com.enterpriseassistant.client.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class AddClientDto {

    private final String taxIdNumber;
    private final String companyName;
    private final AddressDto address;
    private final RepresentativeDto representative;

}
