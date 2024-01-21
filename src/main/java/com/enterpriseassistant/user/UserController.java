package com.enterpriseassistant.user;

import com.enterpriseassistant.user.domain.UserFacade;
import com.enterpriseassistant.user.dto.UpdatePasswordDto;
import com.enterpriseassistant.user.dto.UpdateUserDto;
import com.enterpriseassistant.user.dto.UserDto;
import com.enterpriseassistant.user.dto.UserDtoWithPassword;
import com.enterpriseassistant.user.exception.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
@Api(value = "User Controller", description = "Moduł Api odpowiadający za użytkowników")
public class UserController {

    private final UserFacade userFacade;

    @GetMapping
    public ResponseEntity<List<UserDto>> readAllUsers() {
        return ResponseEntity.ok(userFacade.readAllUsers());
    }

    @GetMapping("/user")
    @ApiOperation(value = "Pobiera dane zalogowanego użytkownika")
    public ResponseEntity<UserDtoWithPassword> readUser(Principal principal) {
        UserDtoWithPassword userDtoWithPassword = userFacade.getUserWithPasswordByUsername(principal.getName());
        return ResponseEntity.ok(userDtoWithPassword);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Usuwa użytkownika")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id,
                                        Principal principal) {
        userFacade.deleteUser(id, principal.getName());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Zastępuje dane użytkownika")
    public ResponseEntity<?> updateUserData(@PathVariable Integer id,
                                            @RequestBody UpdateUserDto updateUserDto,
                                            Principal principal) {
        userFacade.updateUser(id, updateUserDto, principal.getName());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-password/{id}")
    @ApiOperation(value = "Pobiera dane zalogowanego użytkownika")
    public ResponseEntity<?> updateUserPassword(@PathVariable Integer id,
                                                @RequestBody UpdatePasswordDto updatePasswordDto,
                                                Principal principal) {
        userFacade.updatePassword(id, updatePasswordDto, principal.getName());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/enable/{id}")
    @ApiOperation(value = "Aktualizuje status aktywności konta użytkownika")
    public ResponseEntity<?> enableUser(@PathVariable Integer id,
                                        Principal principal) {
        userFacade.enableUser(id, principal.getName());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
