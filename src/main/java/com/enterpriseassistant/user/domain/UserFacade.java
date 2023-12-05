package com.enterpriseassistant.user.domain;

import com.enterpriseassistant.user.dto.RegistrationRequest;
import com.enterpriseassistant.user.dto.UpdatePasswordDto;
import com.enterpriseassistant.user.dto.UpdateUserDto;
import com.enterpriseassistant.user.dto.UserDto;
import com.enterpriseassistant.user.exception.*;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class UserFacade {

    private final UserRepository userRepository;
    private final UserValidationService validationService;

    public UserDto registerUser(RegistrationRequest registrationRequest) {
        verifyAvailability(registrationRequest.getEmail(), registrationRequest.getUsername());
        verifyEmailFormat(registrationRequest.getEmail());

        if (registrationRequest.getFullName().isBlank()) {
            throw new InvalidFullName();
        }

        User user = User.builder()
                .email(registrationRequest.getEmail())
                .username(registrationRequest.getUsername())
                .password(registrationRequest.getPassword())
                .fullName(registrationRequest.getFullName())
                .role(User.Role.USER)
                .enabled(false)
                .build();

        return userRepository.save(user).toDto();
    }

    public List<UserDto> readAllUsers() {
        return userRepository.findAll().stream()
                .map(User::toDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(User::toDto)
                .orElseThrow(UserNotFound::new);
    }

    public UserDto getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(User::toDto)
                .orElseThrow(UserNotFound::new);
    }

    public void deleteUser(Integer id, String username) {
        verifyUserOwnership(id, username);
        verifyUserEnabled(username);

        userRepository.deleteById(id);
    }

    public void updateUser(Integer id, UpdateUserDto updateUserDto, String username) {
        verifyUserOwnership(id, username);
        verifyUserEnabled(username);

        if (updateUserDto.getUsername().isBlank() && updateUserDto.getEmail().isBlank()) {
            throw new InvalidUpdate();
        }

        if (!updateUserDto.getEmail().isEmpty()) {
            verifyEmailFormat(updateUserDto.getEmail());
        }

        verifyAvailability(updateUserDto.getEmail(), updateUserDto.getUsername());

        User user = userRepository.findById(id)
                .orElseThrow(UserNotFound::new);
        UserMapper.mapToUpdate(user, updateUserDto);

        userRepository.save(user);
    }

    public void updatePassword(Integer id, UpdatePasswordDto updatePasswordDto, String username) {
        verifyUserOwnership(id, username);
        verifyUserEnabled(username);

        if (updatePasswordDto.getNewPassword().isBlank() || updatePasswordDto.getOldPassword().isBlank()) {
            throw new IncorrectPassword();
        }

        User user = userRepository.findById(id).orElseThrow(UserNotFound::new);

        if (!updatePasswordDto.getOldPassword().equals(user.getPassword())
                || updatePasswordDto.getNewPassword().equals(user.getPassword())) {
            throw new IncorrectPassword();
        }

        user.setPassword(updatePasswordDto.getNewPassword());
        userRepository.save(user);
    }

    public UserDto enableUser(String email, String usernameOfAdmin) {
        if(!isAdmin(usernameOfAdmin)){
            throw new UserNotEnabled();
        }
        return userRepository.enableAppUser(email).toDto();
    }

    private boolean isAdmin(String username){
        return userRepository.findByUsername(username)
                .map(user -> user.getRole().toString().equals("ADMIN"))
                .orElseThrow(UserNotFound::new);
    }

    private void verifyAvailability(String email, String username) {
        validationService.isUsernameAvailable(username);
        validationService.isEmailAvailable(email);
    }

    private void verifyEmailFormat(String email) {
        validationService.validateEmailFormat(email);
    }

    private void verifyUserOwnership(Integer id, String username) {
        final int userId = getUserByUsername(username).getId();

        if (id == null || userId != id) {
            throw new InvalidUserId();
        }
    }

    private void verifyUserEnabled(String username) {
        if (!getUserByUsername(username).getEnabled()) {
            throw new UserNotEnabled();
        }
    }

}