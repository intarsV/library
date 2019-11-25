package com.accenture.library.service.user;

import com.accenture.library.domain.Authority;
import com.accenture.library.domain.User;
import com.accenture.library.dto.UserResponseDTO;
import com.accenture.library.exceptions.LibraryException;
import com.accenture.library.repository.AuthorityRepository;
import com.accenture.library.repository.UserRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final Long USER_ID = 3L;
    private static final String USER_NAME = "Jānis";
    private static final String PASSWORD = "xxx";
    private static final boolean ENABLED = true;

    private Base64.Encoder encoder = Base64.getEncoder();

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Mock
    private UserRepository repository;

    @Mock
    private AuthorityRepository authorityRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl service;

    @Test
    public void shouldReturnUserList() {
        List<UserResponseDTO> testList = createResponseList();
        when(repository.getAllUsers()).thenReturn(createResponseList());
        assertEquals(testList.size(), service.getUsers().size());
    }

    @Test
    public void shouldCreateNewUser() {
        final String encodedUserName = new String(encoder.encode(USER_NAME.getBytes()));
        final String encodedPassword = new String(encoder.encode(PASSWORD.getBytes()));
        final User newUser = createTestUser();
        when(repository.getByUserName(USER_NAME)).thenReturn(Optional.empty());
        when(repository.save(any(User.class))).thenReturn(newUser);
        when(authorityRepository.findAuthoritiesByAuthorityName("USER")).thenReturn(createAuthority());
        UserResponseDTO userResponseDTO = service.addUser(encodedUserName, encodedPassword);
        assertEquals(USER_ID, userResponseDTO.getId());
    }

    @Test
    public void shouldThrowErrorOnRepositorySave() {
        final String encodedUserName = new String(encoder.encode(USER_NAME.getBytes()));
        final String encodedPassword = new String(encoder.encode(PASSWORD.getBytes()));
        exception.expect(LibraryException.class);
        exception.expectMessage("Database save error");
        service.addUser(encodedUserName, encodedPassword);
    }

    @Test
    public void shouldThrowExceptionDuplicateUserName() {
        final String encodedUserName = new String(encoder.encode(USER_NAME.getBytes()));
        final String encodedPassword = new String(encoder.encode(PASSWORD.getBytes()));
        final User newUser = createTestUser();
        when(repository.getByUserName(USER_NAME)).thenReturn(Optional.of(newUser));
        exception.expect(LibraryException.class);
        exception.expectMessage("User name already exists!");
        service.addUser(encodedUserName, encodedPassword);
    }

    @Test
    public void shouldEnableOrDisableUser() {
        final User testUser = createTestUser();
        when(repository.findById(USER_ID)).thenReturn(Optional.of(testUser));
        when(repository.save(any(User.class))).thenReturn(testUser);
        UserResponseDTO userResponseDTO = service.enableDisableUser(USER_ID);
        assertEquals(USER_ID,userResponseDTO.getId() );
    }

    @Test
    public void shouldThrowErrorOnRepositorySaveForEnableDisable() {
        User user = createTestUser();
        when(repository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(repository.save(user)).thenThrow(new DataAccessException("Bac!") {
        });
        exception.expect(LibraryException.class);
        exception.expectMessage("Database save error");
        service.enableDisableUser(USER_ID);
    }

    @Test
    public void shouldThrowExceptionEnableUserNotFound(){
        when(repository.findById(USER_ID)).thenReturn(Optional.empty());
        exception.expect(LibraryException.class);
        exception.expectMessage("User not found!");
        service.enableDisableUser(USER_ID);
    }

    //AUXILIARY methods

    private Authority createAuthority() {
        final Authority userAuthority = new Authority();
        userAuthority.setAuthorityName("USER");
        return userAuthority;
    }

    private User createTestUser() {
        final User user = new User();
        user.setId(USER_ID);
        user.setUserName(USER_NAME);
        return user;
    }

    private UserResponseDTO createUserResponseDTO() {
        UserResponseDTO responseDTO = new UserResponseDTO(USER_ID, USER_NAME, ENABLED);
        return responseDTO;
    }

    private List<UserResponseDTO> createResponseList() {
        List<UserResponseDTO> list = new ArrayList<>();
        list.add(createUserResponseDTO());
        return list;
    }
}