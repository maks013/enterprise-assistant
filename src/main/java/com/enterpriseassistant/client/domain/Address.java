package com.enterpriseassistant.client.domain;

import com.enterpriseassistant.client.dto.AddressDto;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Builder
@Getter
@Setter
@Entity(name = "clients_addresses")
@AllArgsConstructor
@NoArgsConstructor
class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String postalCode;
    private String city;
    private String street;
    private Integer clientId;

    AddressDto toDto() {
        return AddressDto.builder()
                .postalCode(postalCode)
                .city(city)
                .street(street)
                .build();
    }

}
