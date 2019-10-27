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
@RequestMapping("/api/v1/users")
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

    @PostMapping(value = "/add")
    public ResponseEntity addUser(@RequestBody UserDTO userDTO) {
        final String encodedUserName = userDTO.getUserName();
        final String encodedPassword = userDTO.getPassword();
        return new ResponseEntity<>(userService.addUser(encodedUserName, encodedPassword), HttpStatus.CREATED);
    }

    @PostMapping(value = "/admin/enable")
    public ResponseEntity enableUser(@RequestBody UserDTO userDTO) {
        final Long userId = userDTO.getId();
        return new ResponseEntity<>(userService.enableUser(userId), HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/admin/disable")
    public ResponseEntity disableUser(@RequestBody UserDTO userDTO) {
        final Long userId = userDTO.getId();
        return new ResponseEntity<>(userService.disableUser(userId), HttpStatus.ACCEPTED);
    }
}
