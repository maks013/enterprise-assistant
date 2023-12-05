package com.enterpriseassistant.client.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class RepresentativeDto {

    private final String name;
    private final String surname;
    private final String phoneNumber;
    private final String email;

}
