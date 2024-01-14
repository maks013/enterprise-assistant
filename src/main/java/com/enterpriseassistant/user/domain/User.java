package com.enterpriseassistant.user.domain;

import com.enterpriseassistant.user.dto.UserDto;
import com.enterpriseassistant.user.dto.UserDtoWithPassword;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Builder
@Getter
@Setter
@Entity(name = "users")
@RequiredArgsConstructor
@AllArgsConstructor
class User implements UserDetails {

    enum Role {
        ADMIN, USER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;
    private String username;
    private String password;
    private String fullName;

    @Enumerated(EnumType.STRING)
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

    UserDtoWithPassword toDtoWithPassword() {
        return UserDtoWithPassword.builder()
                .id(id)
                .username(username)
                .fullName(fullName)
                .email(email)
                .password(password)
                .enabled(enabled)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
