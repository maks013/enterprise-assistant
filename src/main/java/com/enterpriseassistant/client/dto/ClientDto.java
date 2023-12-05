package com.enterpriseassistant.client.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ClientDto {

    private final Integer id;
    private final String taxIdNumber;
    private final String companyName;
    private final AddressDto address;
    private final RepresentativeDto representative;

}
