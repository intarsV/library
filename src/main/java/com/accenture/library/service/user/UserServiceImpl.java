package com.accenture.library.service.user;

import com.accenture.library.domain.Authority;
import com.accenture.library.domain.User;
import com.accenture.library.dto.UserResponseDTO;
import com.accenture.library.exceptions.LibraryException;
import com.accenture.library.repository.AuthorityRepository;
import com.accenture.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;
    private AuthorityRepository authorityRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository repository, BCryptPasswordEncoder passwordEncoder, AuthorityRepository authorityRepository) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public List<UserResponseDTO> getUsers() {
        return repository.getAllUsers();
    }

    @Override
    public Long addUser(String encodedUserName, String encodedPassword) {
        Base64.Decoder decoder = Base64.getDecoder();
        final String decodedUserName = new String(decoder.decode(encodedUserName));
        final String decodedPassword = new String(decoder.decode(encodedPassword));
        final Optional<User> foundUser = repository.getByUserName(decodedUserName);
        if (foundUser.isPresent()) {
            throw new LibraryException("User name already exists!");
        }
        final User newUser = createNewUser(decodedUserName, decodedPassword);
        return repository.save(newUser).getId();
    }

    @Override
    public Long enableUser(Long userId) {
        final User user = searchForUser(userId);
        user.setEnabled(true);
        return repository.save(user).getId();
    }

    @Override
    public Long disableUser(Long userId) {
        final User user = searchForUser(userId);
        user.setEnabled(false);
        return repository.save(user).getId();
    }

    //Auxiliary methods

    private User createNewUser(String decryptedUsername, String decryptedPassword) {
        final User newUser = new User();
        newUser.setUserName(decryptedUsername);
        newUser.setPassword(passwordEncoder.encode(decryptedPassword));
        newUser.setEnabled(true);
        newUser.setAuthorities(createAuthorities());
        return newUser;
    }

    private Set<Authority> createAuthorities() {
        final Authority userAuthority = authorityRepository.findAuthoritiesByAuthorityName("USER");
        Set<Authority> authorities = new HashSet<>();
        authorities.add(userAuthority);
        return authorities;
    }

    private User searchForUser(Long userId) {
        final Optional<User> foundUser = repository.findById(userId);
        if (!foundUser.isPresent()) {
            throw new LibraryException("User not found!");
        }
        return foundUser.get();
    }
}
