package com.accenture.library.repository;

import com.accenture.library.domain.User;
import com.accenture.library.dto.UserResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

   @Query("SELECT new com.accenture.library.dto.UserResponseDTO(u.id, u.userName) " +
           "FROM User u  JOIN u.authorities ua WHERE u.enabled=true AND ua=2")
    List<UserResponseDTO> getAllUsers();

    User findByUserName(String userName);

   Optional<User> getByUserName(String decryptedUsername);
}


