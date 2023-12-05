package com.enterpriseassistant.client.domain;

import com.enterpriseassistant.client.dto.RepresentativeDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
class Representative {

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
