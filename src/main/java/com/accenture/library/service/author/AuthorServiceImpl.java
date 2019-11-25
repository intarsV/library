package com.accenture.library.service.author;

import com.accenture.library.domain.Author;
import com.accenture.library.dto.AuthorDTO;
import com.accenture.library.exceptions.LibraryException;
import com.accenture.library.repository.AuthorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private static final String DATABASE_SAVE_ERROR = "Database save error";
    private AuthorRepository repository;
    private Logger logger = LoggerFactory.getLogger(AuthorServiceImpl.class);

    @Autowired
    public AuthorServiceImpl(AuthorRepository repository) {
        this.repository = repository;
    }

    @Override
    public AuthorDTO addAuthor(AuthorDTO authorDTO) {
        validateRequestForDuplicate(authorDTO);
        try {
            authorDTO.setId(repository.save(new Author(authorDTO.getName(), true)).getId());
        } catch (Exception e) {
            logger.error(DATABASE_SAVE_ERROR, e);
            throw new LibraryException(DATABASE_SAVE_ERROR);
        }
        authorDTO.setEnabled(true);
        return authorDTO;
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
        try {
            repository.save(updateAuthor);
        } catch (Exception e) {
            logger.error(DATABASE_SAVE_ERROR, e);
            throw new LibraryException(DATABASE_SAVE_ERROR);
        }
        return updateAuthor.isEnabled();
    }

    //AUXILIARY METHODS

    private void validateRequestForDuplicate(AuthorDTO authorDTO) {
        final Optional<Author> foundAuthor = repository.findByName(authorDTO.getName());
        if (foundAuthor.isPresent()) {
            throw new LibraryException("Duplicate name exists!");
        }
    }
}
