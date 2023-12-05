package com.enterpriseassistant.client.domain;

import com.enterpriseassistant.client.dto.AddClientDto;
import com.enterpriseassistant.client.dto.AddressDto;
import com.enterpriseassistant.client.dto.RepresentativeDto;

class ClientMapper {

    Client fromAddDto(AddClientDto addClientDto) {
        return Client.builder()
                .taxIdNumber(addClientDto.getTaxIdNumber())
                .companyName(addClientDto.getCompanyName())
                .address(addressFromDto(addClientDto.getAddress()))
                .representative(representativeFromDto(addClientDto.getRepresentative()))
                .build();
    }

    Address addressFromDto(AddressDto addressDto) {
        return Address.builder()
                .postalCode(addressDto.getPostalCode())
                .city(addressDto.getCity())
                .street(addressDto.getStreet())
                .build();
    }

    Representative representativeFromDto(RepresentativeDto representativeDto) {
        return Representative.builder()
                .name(representativeDto.getName())
                .surname(representativeDto.getSurname())
                .phoneNumber(representativeDto.getPhoneNumber())
                .email(representativeDto.getEmail())
                .build();
    }
}
