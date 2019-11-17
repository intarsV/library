package com.accenture.library.repository;

import com.accenture.library.domain.Author;
import com.accenture.library.dto.AuthorDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByName(String name);

    @Query("SELECT new com.accenture.library.dto.AuthorDTO(a.id, a.name, a.enabled) FROM Author a WHERE a.enabled=true")
    List<AuthorDTO> getAllAuthors();
}
