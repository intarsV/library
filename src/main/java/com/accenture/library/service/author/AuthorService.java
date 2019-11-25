package com.accenture.library.service.author;

import com.accenture.library.domain.Author;
import com.accenture.library.dto.AuthorDTO;

import java.util.List;

public interface AuthorService {

    AuthorDTO addAuthor(AuthorDTO authorDTO);

    List<AuthorDTO> authorList();

    Author findByName(String name);

    Boolean disableAuthor(Long id);
}
