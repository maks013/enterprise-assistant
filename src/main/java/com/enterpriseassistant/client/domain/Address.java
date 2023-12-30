package com.enterpriseassistant.client.domain;

import com.enterpriseassistant.client.dto.AddressDto;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@Table(name = "clients_addresses")
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
