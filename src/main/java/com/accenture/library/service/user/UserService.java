package com.accenture.library.service.user;

import com.accenture.library.dto.UserResponseDTO;

import java.util.List;

public interface UserService {

    List<UserResponseDTO> getUsers();

    UserResponseDTO addUser(String encryptedUserName, String encryptedPassword);

    UserResponseDTO enableDisableUser(Long userId);
}
