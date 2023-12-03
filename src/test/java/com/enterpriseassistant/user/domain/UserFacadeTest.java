package com.enterpriseassistant.user.domain;

import com.enterpriseassistant.user.dto.RegistrationRequest;
import com.enterpriseassistant.user.dto.UpdatePasswordDto;
import com.enterpriseassistant.user.dto.UpdateUserDto;
import com.enterpriseassistant.user.dto.UserDto;
import com.enterpriseassistant.user.exception.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserFacadeTest {

    InMemoryUserRepository inMemoryUserRepository = new InMemoryUserRepository();

    private final UserFacade userFacade = new UserFacade(
            inMemoryUserRepository,
            new UserDataValidator(inMemoryUserRepository));

    @Test
    void should_throw_exception_when_registering_with_invalid_email_format() {
        //given
        final String invalidEmailExample = "invalidEmailExample";
        //when
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .username("example")
                .password("somePassword")
                .email(invalidEmailExample)
                .fullName("Full name")
                .build();
        //then
        assertThrows(InvalidEmailFormat.class,
                () -> userFacade.registerUser(registrationRequest), "Format of email address is invalid");
    }

    @Test
    void should_throw_exception_when_username_is_already_taken() {
        //given
        final String username = "exampleUsername";
        //and
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .username(username)
                .password("somePassword")
                .email("someEmail@example.com")
                .fullName("Name Surname")
                .build();
        RegistrationRequest registrationRequestWithSameUsername = RegistrationRequest.builder()
                .username(username)
                .password("somePassword")
                .email("someEmail2@example.com")
                .fullName("Name Surname")
                .build();
        //when
        userFacade.registerUser(registrationRequest);
        //then
        assertThrows(TakenUsername.class,
                () -> userFacade.registerUser(registrationRequestWithSameUsername),
                "This username is already taken");
    }

    @Test
    void should_throw_exception_when_email_is_already_taken() {
        //given
        String email = "someEmail@example.com";
        //and
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .username("usernameExample1")
                .password("somePassword")
                .email(email)
                .fullName("Name Surname")
                .build();
        RegistrationRequest registrationRequestWithSameEmail = RegistrationRequest.builder()
                .username("usernameExample2")
                .password("somePassword")
                .email(email)
                .fullName("Name Surname")
                .build();
        //when
        userFacade.registerUser(registrationRequest);
        //then
        assertThrows(TakenEmail.class,
                () -> userFacade.registerUser(registrationRequestWithSameEmail),
                "This username is already taken");
    }

    @Test
    void should_throw_exception_when_registering_without_name() {
        //given
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .username("usernameExample1")
                .password("somePassword")
                .email("email@someEmail.com")
                .fullName(" ")
                .build();
        //when
        //then
        assertThrows(InvalidFullName.class, () -> userFacade.registerUser(registrationRequest));
    }

    @Test
    void should_register_user_successfully() {
        //given
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .username("usernameExample1")
                .password("somePassword")
                .email("email@someEmail.com")
                .fullName("Name Surname")
                .build();
        //when
        UserDto userDto = userFacade.registerUser(registrationRequest);
        //then
        assertAll(
                () -> assertEquals(registrationRequest.getUsername(), userDto.getUsername()),
                () -> assertEquals(registrationRequest.getEmail(), userDto.getEmail())
        );
    }

    @Test
    void should_findAll_users_size_should_be_2() {
        //given
        //when
        final int users = userFacade.readAllUsers().size();
        //then
        assertEquals(2, users);
    }

    @Test
    void should_find_user_by_username() {
        //given
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .username("usernameExample1")
                .password("somePassword")
                .email("email@someEmail.com")
                .fullName("Name Surname")
                .build();
        //when
        userFacade.registerUser(registrationRequest);
        UserDto userDto = userFacade.getUserByUsername("usernameExample1");
        //then
        assertAll(
                () -> assertEquals(3, userDto.getId()),
                () -> assertEquals("email@someEmail.com", userDto.getEmail())
        );
    }

    @Test
    void should_throw_exception_when_user_not_found_by_username() {
        //given
        final String usernameOfNotExistingUser = "example";
        //when
        //then
        assertThrows(UserNotFound.class, () -> userFacade.getUserByUsername(usernameOfNotExistingUser),
                "User not found");
    }

    @Test
    void should_find_user_by_email() {
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .username("usernameExample1")
                .password("somePassword")
                .email("email@someEmail.com")
                .fullName("Name Surname")
                .build();
        //when
        userFacade.registerUser(registrationRequest);
        UserDto userDto = userFacade.getUserByEmail("email@someEmail.com");
        //then
        assertAll(
                () -> assertEquals("usernameExample1", userDto.getUsername()),
                () -> assertEquals(3, userDto.getId())
        );
    }


    @Test
    void should_throw_exception_when_user_not_found_by_email() {
        //given
        final String emailOfNotExistingUser = "example@example.com";
        //when
        //then
        assertThrows(UserNotFound.class, () -> userFacade.getUserByEmail(emailOfNotExistingUser),
                "User not found");
    }

    @Test
    void should_delete_user_by_username() {
        //given
        final int sizeBeforeDeleteUser = userFacade.readAllUsers().size();
        //when
        userFacade.deleteUser(1, "admin");
        final int sizeAfterDeleteUser = userFacade.readAllUsers().size();
        //then
        assertEquals(1, sizeBeforeDeleteUser - sizeAfterDeleteUser);
    }

    @Test
    void should_throw_userNotFoundException_while_deleting_user_when_user_with_that_username_notExists() {
        //given
        final String usernameOfNotExistingUser = "example";
        //when
        //then
        assertThrows(UserNotFound.class, () -> userFacade.deleteUser(1, usernameOfNotExistingUser));
    }

    @Test
    void should_throw_userNotFoundException_while_deleting_user_when_id_is_null() {
        //given
        //when
        //then
        assertThrows(InvalidUserId.class, () -> userFacade.deleteUser(null, "admin"));
    }

    @Test
    void should_updateUser_data() {
        //given
        final String usernameBefore = "admin", emailBefore = "admin@admin.com";
        final String usernameAfter = "updatedUsername", emailAfter = "updated@updated.com";

        //and
        UpdateUserDto updateUserDto = UpdateUserDto.builder()
                .username(usernameAfter)
                .email(emailAfter)
                .build();
        //when
        userFacade.updateUser(1, updateUserDto, usernameBefore);
        UserDto userAfterUpdate = userFacade.getUserByUsername("updatedUsername");
        //then
        assertAll(
                () -> assertNotEquals(usernameBefore, userAfterUpdate.getUsername()),
                () -> assertNotEquals(emailBefore, userAfterUpdate.getEmail())
        );
    }

    @Test
    void should_throw_exception_while_updating_when_email_address_format_is_invalid() {
        //given
        final String invalidEmailExample = "invalidEmailExample";
        //when
        UpdateUserDto updateUserDto = UpdateUserDto.builder()
                .username("")
                .email(invalidEmailExample)
                .build();
        //then
        assertThrows(InvalidEmailFormat.class, () -> userFacade.updateUser(1, updateUserDto, "admin"));
    }

    @Test
    void should_throw_exception_while_updating_user_data_when_email_is_already_taken() {
        //given
        final String username = "admin";
        final int userId = 1;
        final String takenEmail = userFacade.getUserByUsername("user2").getEmail();
        //when
        UpdateUserDto updateUserDto = UpdateUserDto.builder()
                .username("")
                .email(takenEmail)
                .build();
        //then
        assertThrows(TakenEmail.class, () -> userFacade.updateUser(userId, updateUserDto, username));
    }

    @Test
    void should_throw_exception_while_updating_user_data_when_username_is_already_taken() {
        //given
        final String user = "admin", takenUsername = "user2";
        final int userId = userFacade.getUserByUsername(user).getId();
        //when
        UpdateUserDto updateUserDto = UpdateUserDto.builder()
                .username(takenUsername)
                .email("example@updated.com")
                .build();
        //then
        assertThrows(TakenUsername.class, () -> userFacade.updateUser(userId, updateUserDto, user));
    }

    @Test
    void should_throw_exception_when_old_password_is_blank() {
        //given
        UpdatePasswordDto updatePasswordDto = UpdatePasswordDto.builder()
                .oldPassword("")
                .newPassword("newPassword")
                .build();
        //when
        //then
        assertThrows(IncorrectPassword.class,
                () -> userFacade.updatePassword(1, updatePasswordDto, "admin"),
                "Old password is blank");
    }

    @Test
    void should_throw_exception_when_new_password_is_blank() {
        //given
        UpdatePasswordDto updatePasswordDto = UpdatePasswordDto.builder()
                .oldPassword("oldPassword")
                .newPassword("")
                .build();
        //when
        //then
        assertThrows(IncorrectPassword.class,
                () -> userFacade.updatePassword(1, updatePasswordDto, "admin"),
                "New password is blank");
    }

    /*
       TODO
        */
    @Test
    void should_throw_exception_when_old_password_is_incorrect() {
        //given
        UpdatePasswordDto updatePasswordDto = UpdatePasswordDto.builder()
                .oldPassword("incorrectOldPassword")
                .newPassword("newPassword")
                .build();
        //when
        //then
        assertThrows(IncorrectPassword.class,
                () -> userFacade.updatePassword(1, updatePasswordDto, "admin"),
                "Password is incorrect");
    }

    @Test
    void should_throw_exception_when_new_password_matches_old_password() {
        //given
        UpdatePasswordDto updatePasswordDto = UpdatePasswordDto.builder()
                .oldPassword("oldPassword")
                .newPassword("oldPassword")
                .build();
        //when
        //then
        assertThrows(IncorrectPassword.class,
                () -> userFacade.updatePassword(1, updatePasswordDto, "admin"),
                "Password is incorrect");
    }

    @Test
    void admin_should_be_able_to_enable_user() {
        //given
        final String usernameOfAdmin = "admin";
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .username("usernameExample1")
                .password("somePassword")
                .email("email@someEmail.com")
                .fullName("Name Surname")
                .build();
        //when
        userFacade.registerUser(registrationRequest);
        UserDto userDto = userFacade.enableUser("email@someEmail.com", usernameOfAdmin);
        //then
        assertTrue(userDto.getEnabled());
    }

    @Test
    void should_throw_exception_when_user_tries_to_enable_user() {
        //given
        final String usernameOfDefaultUser = "user2";
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .username("usernameExample1")
                .password("somePassword")
                .email("email@someEmail.com")
                .fullName("Name Surname")
                .build();
        //when
        userFacade.registerUser(registrationRequest);
        //then
        assertThrows(UserNotEnabled.class, () -> userFacade.enableUser("email@someEmail.com", usernameOfDefaultUser));
    }
}
