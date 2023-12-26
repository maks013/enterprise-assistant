package com.enterpriseassistant.user;

import com.enterpriseassistant.infrastructure.security.jwt.JwtAuthenticator;
import com.enterpriseassistant.user.domain.UserFacade;
import com.enterpriseassistant.user.dto.LoginRequestDto;
import com.enterpriseassistant.user.dto.LoginResponseDto;
import com.enterpriseassistant.user.dto.RegistrationRequest;
import com.enterpriseassistant.user.dto.UserDto;
import com.enterpriseassistant.user.exception.InvalidEmailFormat;
import com.enterpriseassistant.user.exception.TakenEmail;
import com.enterpriseassistant.user.exception.TakenUsername;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class LoginAndRegisterController {

    private final JwtAuthenticator jwtAuthenticator;
    private final UserFacade userFacade;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        final LoginResponseDto loginResponseDto = jwtAuthenticator.authenticate(loginRequestDto);
        return ResponseEntity.ok(loginResponseDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody RegistrationRequest registrationRequest) {
        try {
            UserDto userDto = userFacade.registerUser(registrationRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
        } catch (InvalidEmailFormat | TakenEmail | TakenUsername exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/register-admin")
    public ResponseEntity<UserDto> registerAdmin(@Valid @RequestBody RegistrationRequest registrationRequest) {
        try {
            UserDto userDto = userFacade.registerAdminForTest(registrationRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
        } catch (InvalidEmailFormat | TakenEmail | TakenUsername exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
