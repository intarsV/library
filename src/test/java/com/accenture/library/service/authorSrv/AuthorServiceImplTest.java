package com.accenture.library.service.authorSrv;

import com.accenture.library.domain.Author;
import com.accenture.library.dto.AuthorDTO;
import com.accenture.library.exceptions.LibraryException;
import com.accenture.library.repository.AuthorRepository;
import com.accenture.library.service.author.AuthorServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthorServiceImplTest {

    private static final Long ID = 1L;
    private static final String AUTHOR_NAME = "Janka";

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Mock
    private AuthorRepository repository;

    @InjectMocks
    private AuthorServiceImpl service;

    @Test
    public void shouldReturnAuthorList() {
        List<AuthorDTO> list = createList();
        when(repository.getAllAuthors()).thenReturn(list);
        List<AuthorDTO> result = service.authorList();
        assertEquals(list.get(0).getName(), result.get(0).getName());
    }

    @Test
    public void shouldAddAuthor() {
        Author author = createAuthor();
        AuthorDTO authorDTO = createDTO();
        when(repository.findByName(AUTHOR_NAME)).thenReturn(Optional.empty());
        when(repository.save(new Author(AUTHOR_NAME, true))).thenReturn(author);
        AuthorDTO returnedDTO = service.addAuthor(authorDTO);
        assertEquals(ID, returnedDTO.getId());
    }

    @Test
    public void shouldThrowErrorOnDuplicateSave() {
        Author author = createAuthor();
        AuthorDTO authorDTO = createDTO();
        when(repository.findByName(AUTHOR_NAME)).thenReturn(Optional.of(author));
        exception.expect(LibraryException.class);
        exception.expectMessage("Duplicate name exists!");
        service.addAuthor(authorDTO);
    }

    @Test
    public void shouldThrowErrorOnRepositorySave() {
        AuthorDTO authorDTO = createDTO();
        exception.expect(LibraryException.class);
        exception.expectMessage("Database save error");
        service.addAuthor(authorDTO);
    }

    @Test
    public void shouldFindByName() {
        Author author = createAuthor();
        when(repository.findByName(AUTHOR_NAME)).thenReturn(Optional.of(author));
        assertEquals(author, service.findByName(AUTHOR_NAME));
    }

    @Test
    public void shouldThrowErrorOnFindByName() {
        when(repository.findByName(AUTHOR_NAME)).thenReturn(Optional.empty());
        exception.expect(LibraryException.class);
        exception.expect(LibraryException.class);
        exception.expectMessage("No such author found!");
        service.findByName(AUTHOR_NAME);
    }

    @Test
    public void shouldDisableAuthor() {
        Author author = createAuthor();
        when(repository.findById(ID)).thenReturn(Optional.of(author));
        when(repository.save(any(Author.class))).thenReturn(author);
        assertFalse(service.disableAuthor(ID));
    }

    @Test
    public void shouldThrowErrorWhenDisableAuthor() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        exception.expect(LibraryException.class);
        exception.expectMessage("No such author with specified id");
        service.disableAuthor(ID);
    }

    @Test
    public void shouldThrowErrorOnRepositorySaveForDisable() {
        Author author = createAuthor();
        when(repository.findById(ID)).thenReturn(Optional.of(author));
        when(repository.save(author)).thenThrow(new DataAccessException("Bac!") {
        });
        exception.expect(LibraryException.class);
        exception.expectMessage("Database save error");
        service.disableAuthor(ID);
    }

    //Auxiliary methods

    private AuthorDTO createDTO() {
        return new AuthorDTO(ID, AUTHOR_NAME, false);
    }

    private List<AuthorDTO> createList() {
        List<AuthorDTO> list = new ArrayList<>();
        list.add(createDTO());
        return list;
    }

    private Author createAuthor() {
        return new Author(ID, AUTHOR_NAME, false);
    }
}