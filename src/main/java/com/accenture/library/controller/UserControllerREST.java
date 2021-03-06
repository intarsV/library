package com.accenture.library.controller;

import com.accenture.library.dto.UserDTO;
import com.accenture.library.dto.UserResponseDTO;
import com.accenture.library.service.user.UserService;
import com.accenture.library.service.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public List<UserResponseDTO> getAllUsers() {
        return userService.getUsers();
    }

    @PostMapping()
    public ResponseEntity addUser(@Valid @RequestBody final UserDTO userDTO) {
        final String encodedUserName = userDTO.getUserName();
        final String encodedPassword = userDTO.getPassword();
        return new ResponseEntity<>(userService.addUser(encodedUserName, encodedPassword), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity enableUser(@PathVariable Long id) {
        return new ResponseEntity<>(userService.enableDisableUser(id), HttpStatus.ACCEPTED);
    }
}
