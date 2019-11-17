package com.accenture.library.service.user;

import com.accenture.library.domain.Authority;
import com.accenture.library.domain.User;
import com.accenture.library.dto.UserResponseDTO;
import com.accenture.library.exceptions.LibraryException;
import com.accenture.library.repository.AuthorityRepository;
import com.accenture.library.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private static final String DATABASE_SAVE_ERROR = "Database save error";
    private UserRepository repository;
    private AuthorityRepository authorityRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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
    public UserResponseDTO addUser(String encodedUserName, String encodedPassword) {
        Base64.Decoder decoder = Base64.getDecoder();
        final String decodedUserName = new String(decoder.decode(encodedUserName));
        final String decodedPassword = new String(decoder.decode(encodedPassword));
        validateRequestForDuplicateName(decodedUserName);
        final User newUser = createNewUser(decodedUserName, decodedPassword);
        final UserResponseDTO newUserDto = new UserResponseDTO();
        try {
            newUserDto.setId(repository.save(newUser).getId());
        } catch (Exception e) {
            logger.error(DATABASE_SAVE_ERROR, e);
            throw new LibraryException(DATABASE_SAVE_ERROR);
        }
        newUserDto.setUserName(newUser.getUserName());
        newUserDto.setEnabled(true);
        return newUserDto;
    }

    @Override
    public UserResponseDTO enableDisableUser(Long userId) {
        final User user = searchForUser(userId);
        boolean currentStatus = user.isEnabled();
        user.setEnabled(!currentStatus);
        final UserResponseDTO disabledUserDto = new UserResponseDTO();
        try {
            disabledUserDto.setId(repository.save(user).getId());
        } catch (Exception e) {
            logger.error(DATABASE_SAVE_ERROR, e);
            throw new LibraryException(DATABASE_SAVE_ERROR);
        }
        return disabledUserDto;
    }

    //Auxiliary methods

    private void validateRequestForDuplicateName(String decodedUserName) {
        final Optional<User> foundUser = repository.getByUserName(decodedUserName);
        if (foundUser.isPresent()) {
            throw new LibraryException("User name already exists!");
        }
    }

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
