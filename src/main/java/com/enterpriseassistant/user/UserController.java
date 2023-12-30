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
        userFacade.deleteUser(id, principal.getName());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserData(@PathVariable Integer id,
                                            @RequestBody UpdateUserDto updateUserDto,
                                            Principal principal) {
        userFacade.updateUser(id, updateUserDto, principal.getName());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-password/{id}")
    public ResponseEntity<?> updateUserPassword(@PathVariable Integer id,
                                                @RequestBody UpdatePasswordDto updatePasswordDto,
                                                Principal principal) {
        userFacade.updatePassword(id, updatePasswordDto, principal.getName());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/enable/{id}")
    public ResponseEntity<?> enableUser(@PathVariable Integer id,
                                        Principal principal) {
        userFacade.enableUser(id, principal.getName());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
