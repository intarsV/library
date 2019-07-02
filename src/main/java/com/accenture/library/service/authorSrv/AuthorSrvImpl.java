package com.accenture.library.service.authorSrv;

import com.accenture.library.domain.Author;
import com.accenture.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorSrvImpl implements AuthorSrv {

    private AuthorRepository repository;

    @Autowired
    public AuthorSrvImpl(AuthorRepository repository) {
        this.repository = repository;
    }


    @Override
    public Long saveAuthor(String author) {
        return repository.save(new Author(author)).getId();
    }

    @Override
    public List<Author> authorList() {
        return repository.findAll();
    }

    @Override
    public Author findByName(String authorName) {
        Optional<Author> foundAuthor = repository.findByAuthorName(authorName);
        return foundAuthor.get();
    }
}
