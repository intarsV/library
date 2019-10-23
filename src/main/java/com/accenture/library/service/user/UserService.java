package com.accenture.library.service.user;

import com.accenture.library.dto.UserResponseDTO;

import java.util.List;

public interface UserService {

    List<UserResponseDTO> getUsers();

    Long addUser(String encryptedUserName, String encryptedPassword);

    String disableUser(Long userId);

}
