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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
    public void shouldSaveAuthor(){
        Author author = createAuthor();
        when(repository.findByName(AUTHOR_NAME)).thenReturn(Optional.empty());
        when(repository.save(new Author(AUTHOR_NAME, false))).thenReturn(author);
        assertEquals(ID , service.saveAuthor(AUTHOR_NAME));
    }

    @Test
    public void shouldThrowErrorOnDuplicateSave(){
        Author author = createAuthor();
        when(repository.findByName(AUTHOR_NAME)).thenReturn(Optional.of(author));
        exception.expect(LibraryException.class);
        exception.expectMessage("Duplicate name exists!");
        service.saveAuthor(AUTHOR_NAME);
    }

    @Test
    public void shouldFindByName(){
        Author author = createAuthor();
        when(repository.findByName(AUTHOR_NAME)).thenReturn(Optional.of(author));
        assertEquals(author, service.findByName(AUTHOR_NAME));
    }

    @Test
    public void shouldThrowErrorOnFindByName(){
        when(repository.findByName(AUTHOR_NAME)).thenReturn(Optional.empty());
        exception.expect(LibraryException.class);
        exception.expect(LibraryException.class);
        exception.expectMessage("No such author found!");
        service.findByName(AUTHOR_NAME);
    }

    @Test
    public void shouldDeleteAuthor(){
        Author author = createAuthor();
        when(repository.findById(1L)).thenReturn(Optional.of(author));
        when(repository.save(any(Author.class))).thenReturn(author);
        assertTrue(service.disableAuthor(ID));
    }

    @Test
    public void shouldThrowErrorWhenDelete(){
        when(repository.findById(1L)).thenReturn(Optional.empty());
        exception.expect(LibraryException.class);
        exception.expectMessage("No such author with id: "+ID );
        service.disableAuthor(ID);
    }

    //Auxiliary methods

    private AuthorDTO createDTO() {
        return new AuthorDTO(ID, AUTHOR_NAME, false);
    }

    private List<AuthorDTO> createList(){
        List<AuthorDTO> list=new ArrayList<>();
        list.add(createDTO());
        return list;
    }

    private Author createAuthor() {
        return new Author( ID, AUTHOR_NAME, false);
    }
}