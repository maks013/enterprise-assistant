package com.enterpriseassistant.client.domain;

import com.enterpriseassistant.client.dto.AddressDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
class Address {

    private Integer id;
    private String postalCode;
    private String city;
    private String street;
    private Integer clientId;

    AddressDto toDto(){
        return AddressDto.builder()
                .postalCode(postalCode)
                .city(city)
                .street(street)
                .build();
    }

}
