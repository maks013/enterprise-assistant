package com.enterpriseassistant.user.domain;

import com.enterpriseassistant.user.dto.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
class User{

    enum Role {
        ADMIN, USER
    }

    private Integer id;
    private String email;
    private String username;
    private String password;
    private String fullName;
    private Role role;
    private Boolean enabled;

    UserDto toDto() {
        return UserDto.builder()
                .id(id)
                .email(email)
                .username(username)
                .fullName(fullName)
                .enabled(enabled)
                .build();
    }

}
