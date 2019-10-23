package com.accenture.library.service.user;

import com.accenture.library.domain.User;
import com.accenture.library.dto.UserResponseDTO;
import com.accenture.library.exceptions.LibraryException;
import com.accenture.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository repository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserResponseDTO> getUsers() {
        return repository.getAllUsers();
    }


    @Override
    public Long addUser(String encryptedUserName, String encryptedPassword) {
        Base64.Decoder decoder = Base64.getDecoder();
        final String decryptedUsername = new String(decoder.decode(encryptedUserName));
        final String decryptedPassword = new String(decoder.decode(encryptedPassword));
        final Optional<User> foundUser = repository.getByUserName(decryptedUsername);
        if (foundUser.isPresent()) {
            throw new LibraryException("User name already exists!");
        }
        final User newUser = new User();
        newUser.setUserName(decryptedUsername);
        newUser.setPassword(passwordEncoder.encode(decryptedPassword));
        return repository.save(newUser).getId();
    }

    @Override
    public String disableUser(Long user) {
        return null;
    }
}
