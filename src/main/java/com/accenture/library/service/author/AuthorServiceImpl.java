package com.accenture.library.service.author;

import com.accenture.library.domain.Author;
import com.accenture.library.dto.AuthorDTO;
import com.accenture.library.exceptions.LibraryException;
import com.accenture.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository repository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository repository) {
        this.repository = repository;
    }


    @Override
    public Long saveAuthor(String name) {
        final Optional<Author> foundAuthor = repository.findByName(name);
        if (foundAuthor.isPresent()) {
            throw new LibraryException("Duplicate name exists!");
        }
        return repository.save(new Author(name, true)).getId();
    }

    @Override
    public List<AuthorDTO> authorList() {
        return repository.getAllAuthors();
    }

    @Override
    public Author findByName(String name) {
        final Optional<Author> foundAuthor = repository.findByName(name);
        if (!foundAuthor.isPresent()) {
            throw new LibraryException("No such author found!");
        }
        return foundAuthor.get();
    }

    @Override
    public Boolean disableAuthor(Long id) {
        final Optional<Author> findAuthor = repository.findById(id);
        if (!findAuthor.isPresent()) {
            throw new LibraryException("No such author with specified id");
        }
        final Author updateAuthor = findAuthor.get();
        updateAuthor.setEnabled(false);
        repository.save(updateAuthor);
        return updateAuthor.isEnabled();
    }
}
