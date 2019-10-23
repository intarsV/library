package com.accenture.library.service.author;

import com.accenture.library.domain.Author;
import com.accenture.library.dto.AuthorDTO;
import org.springframework.dao.DataRetrievalFailureException;

import java.util.List;

public interface AuthorService {

    Long saveAuthor(String name);
    List<AuthorDTO> authorList();
    Author findByName(String name) throws DataRetrievalFailureException;
    Boolean deleteAuthor(Long id);
}
