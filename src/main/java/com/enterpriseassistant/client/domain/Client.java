package com.enterpriseassistant.client.domain;

import com.enterpriseassistant.client.dto.ClientDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
class Client {

    private Integer id;
    private String taxIdNumber;
    private String companyName;
    private Address address;
    private Representative representative;

    ClientDto toDto(){
        return ClientDto.builder()
                .id(id)
                .taxIdNumber(taxIdNumber)
                .companyName(companyName)
                .address(address.toDto())
                .representative(representative.toDto())
                .build();
    }
}
