package com.enterpriseassistant.client.domain;

import com.enterpriseassistant.client.dto.ClientDto;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@Table(name = "clients")
@AllArgsConstructor
@NoArgsConstructor
class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String taxIdNumber;
    private String companyName;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToOne
    @JoinColumn(name = "representative_id")
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
