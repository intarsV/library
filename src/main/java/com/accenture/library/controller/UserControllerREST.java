package com.accenture.library.controller;

import com.accenture.library.dto.UserDTO;
import com.accenture.library.dto.UserResponseDTO;
import com.accenture.library.service.user.UserService;
import com.accenture.library.service.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/users")
public class UserControllerREST {

    private UserService userService;

    @Autowired
    public UserControllerREST(UserServiceImpl userService) {
        this.userService = userService;
    }


    @GetMapping
    public List<UserResponseDTO> getAllBooks() {
        return userService.getUsers();
    }

    @PostMapping(value = "/add-user")
    public ResponseEntity<> addUser(@RequestBody UserDTO userDTO) {
        System.out.println(userDTO);
        System.out.println("here");

        final String encryptedUserName = userDTO.getUserName();
        final String encryptedPassword = userDTO.getPassword();
        return new ResponseEntity<>(userService.addUser(encryptedUserName, encryptedPassword), HttpStatus.CREATED);
    }

    @PostMapping(value = "/remove-user")
    public ResponseEntity disableUser(@RequestBody UserDTO userDTO) {
        final Long userId = userDTO.getId();
        return new ResponseEntity<>(userService.disableUser(userId), HttpStatus.ACCEPTED);
    }
}
