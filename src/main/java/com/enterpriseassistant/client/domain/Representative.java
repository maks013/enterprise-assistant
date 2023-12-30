package com.enterpriseassistant.client.domain;

import com.enterpriseassistant.client.dto.RepresentativeDto;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@Table(name = "clients_representatives")
@AllArgsConstructor
@NoArgsConstructor
class Representative {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private Integer clientId;

    RepresentativeDto toDto() {
        return RepresentativeDto.builder()
                .name(name)
                .surname(surname)
                .phoneNumber(phoneNumber)
                .email(email)
                .build();
    }
}
