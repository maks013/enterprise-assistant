package com.enterpriseassistant.user.domain;

import com.enterpriseassistant.user.exception.InvalidEmailFormat;
import com.enterpriseassistant.user.exception.TakenEmail;
import com.enterpriseassistant.user.exception.TakenUsername;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
class UserDataValidator implements UserValidationService {

    private final UserRepository userRepository;

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";

    @Override
    public void isUsernameAvailable(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new TakenUsername();
        }
    }

    @Override
    public void isEmailAvailable(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new TakenEmail();
        }
    }

    @Override
    public void validateEmailFormat(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new InvalidEmailFormat();
        }
    }

}
