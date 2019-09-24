package com.accenture.library.service.authorSrv;

import com.accenture.library.domain.Author;
import org.springframework.dao.DataRetrievalFailureException;

import java.util.List;

public interface AuthorSrv {

    Long saveAuthor(String name);
    List<Author> authorList();
    Author findByName(String name) throws DataRetrievalFailureException;
    Boolean deleteAuthor(Long id);
}
