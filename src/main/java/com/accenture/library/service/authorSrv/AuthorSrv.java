package com.accenture.library.service.authorSrv;

import com.accenture.library.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorSrv {

    Long saveAuthor(String name);
    List<Author> authorList();
    Author findByName(String name);
}
