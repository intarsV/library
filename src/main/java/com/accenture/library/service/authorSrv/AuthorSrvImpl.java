package com.accenture.library.service.authorSrv;

import com.accenture.library.domain.Author;
import com.accenture.library.dto.AuthorDTO;
import com.accenture.library.exceptions.LibraryException;
import com.accenture.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
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
    public Long saveAuthor(String name) throws LibraryException {
        Optional<Author> foundAuthor = repository.findByName(name);
        if (foundAuthor.isPresent()) {
            throw new LibraryException("Duplicate name exists!");
        }
        return repository.save(new Author(name, false)).getId();
    }

    @Override
    public List<AuthorDTO> authorList() {
        return repository.getAllAuthors();
    }

    @Override
    public Author findByName(String name) throws LibraryException {
        Optional<Author> foundAuthor = repository.findByName(name);
        if (!foundAuthor.isPresent()) {
            throw new LibraryException("No such author found!");
        }
        return foundAuthor.get();
    }

    @Override
    public Boolean deleteAuthor(Long id) {
        Optional<Author> findAuthor = repository.findById(id);
        if (!findAuthor.isPresent()) {
            throw new LibraryException("No such author with id: " + id);
        }
        Author updateAuthor = findAuthor.get();
        updateAuthor.setDeleted(true);
        repository.save(updateAuthor);
        return true;
    }
}
