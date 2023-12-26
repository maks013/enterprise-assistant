package com.enterpriseassistant.user.domain;

import com.enterpriseassistant.user.dto.UpdateUserDto;

class UserMapper {

    static void mapToUpdate(User user, UpdateUserDto updateUserDto) {
        user.setUsername(updateUserDto.getUsername().isEmpty()
                ? user.getUsername() : updateUserDto.getUsername());
        user.setFullName(updateUserDto.getFullName().isEmpty()
                ? user.getFullName() : updateUserDto.getFullName());
        user.setEmail(updateUserDto.getEmail().isEmpty()
                ? user.getEmail() : updateUserDto.getEmail());
    }
}
