package com.enterpriseassistant.user;

import com.enterpriseassistant.user.domain.UserFacade;
import com.enterpriseassistant.user.dto.UpdatePasswordDto;
import com.enterpriseassistant.user.dto.UpdateUserDto;
import com.enterpriseassistant.user.dto.UserDto;
import com.enterpriseassistant.user.dto.UserDtoWithPassword;
import com.enterpriseassistant.user.exception.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserFacade userFacade;

    @GetMapping
    public ResponseEntity<List<UserDto>> readAllUsers() {
        return ResponseEntity.ok(userFacade.readAllUsers());
    }

    @GetMapping("/user")
    public ResponseEntity<UserDtoWithPassword> readUser(Principal principal) {
        UserDtoWithPassword userDtoWithPassword = userFacade.getUserWithPasswordByUsername(principal.getName());
        return ResponseEntity.ok(userDtoWithPassword);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id,
                                        Principal principal) {
        try {
            userFacade.deleteUser(id, principal.getName());
            return ResponseEntity.noContent().build();
        } catch (UserNotFound exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (InvalidUserId exception) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserData(@PathVariable Integer id,
                                            @RequestBody UpdateUserDto updateUserDto,
                                            Principal principal) {
        try {
            userFacade.updateUser(id, updateUserDto, principal.getName());
            return ResponseEntity.ok().build();
        } catch (UserNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (TakenEmail | TakenUsername |
                InvalidEmailFormat | InvalidUserId | InvalidUpdate exception) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        } catch (UserNotEnabled exception) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PutMapping("/update-password/{id}")
    public ResponseEntity<?> updateUserPassword(@PathVariable Integer id,
                                                @RequestBody UpdatePasswordDto updatePasswordDto,
                                                Principal principal) {
        try {
            userFacade.updatePassword(id, updatePasswordDto, principal.getName());
            return ResponseEntity.ok().build();
        } catch (UserNotFound exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IncorrectPassword exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping("/enable/{id}")
    public ResponseEntity<?> enableUser(@PathVariable Integer id,
                                        Principal principal) {
        try {
            userFacade.enableUser(id, principal.getName());
        } catch (UserNotEnabled exception) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
