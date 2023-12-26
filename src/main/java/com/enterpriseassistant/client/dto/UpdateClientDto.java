package com.enterpriseassistant.client.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateClientDto {

    private final String taxIdNumber;
    private final String companyName;

}
