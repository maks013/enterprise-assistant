package com.enterpriseassistant.client.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateRepresentativeDto {

    private final String name;
    private final String surname;
    private final String phoneNumber;
    private final String email;

}
