package com.accenture.library.service.bookSrv;

import com.accenture.library.domain.Author;
import com.accenture.library.domain.Book;
import com.accenture.library.dto.BookDTO;
import com.accenture.library.exceptions.LibraryException;
import com.accenture.library.repository.BookRepository;
import com.accenture.library.service.author.AuthorServiceImpl;
import com.accenture.library.service.book.BookServiceImpl;
import org.junit.Assert;
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

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceImplTest {

    private static final Long ID = 1L;
    private static final String TITLE = "SuperBook";
    private static final String AUTHOR_NAME = "Janka";
    private static final String GENRE = "NOVEL";
    private static final int COPIES = 3;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorServiceImpl authorSrv;

    @InjectMocks
    BookServiceImpl service;

    @Test
    public void shouldSaveBook() {
        BookDTO bookDTO = createDTO();
        Author author = createAuthor();
        Book book = new Book(TITLE, author, GENRE, COPIES, COPIES, true);
        book.setId(ID);
        when(authorSrv.findByName(AUTHOR_NAME)).thenReturn(author);
        when(bookRepository.save(new Book(TITLE, author, GENRE, COPIES, COPIES, true))).thenReturn(book);
        BookDTO responseBookDTO = service.addBook(bookDTO);
        assertEquals(ID, responseBookDTO.getId());
    }

    @Test
    public void shouldThrowErrorOnSaveIsParamMissing() {
        BookDTO bookDTO = createDTO();
        bookDTO.setTitle("");
        bookDTO.setCopies(0);
        exception.expect(LibraryException.class);
        exception.expectMessage("Bad request - require all field");
        service.addBook(bookDTO);
    }

    @Test
    public void shouldDeleteBook() {
        final Author author = createAuthor();
        Book book = new Book(TITLE, author, GENRE, COPIES, COPIES, false);
        when(bookRepository.findById(ID)).thenReturn(Optional.of(book));
        assertFalse(service.disableBook(ID));
    }

    @Test
    public void shouldThrowErrorOnDelete() {
        when(bookRepository.findById(ID)).thenReturn(Optional.empty());
        exception.expect(LibraryException.class);
        exception.expectMessage("No such book found");
        service.disableBook(ID);
    }

    @Test
    public void shouldReturnListOnParameterSearch() {
        final List<BookDTO> mockList = createList();
        when(bookRepository.findByParameters(null, AUTHOR_NAME, null)).thenReturn(mockList);
        Assert.assertEquals(mockList, service.getByParameters(null, AUTHOR_NAME, null));
    }

    @Test
    public void shouldReturnListOfAllBooksOnParameterSearchWithoutAnyValidParam() {
        final List<BookDTO> mockList = createList();
        when(bookRepository.getAllBooks()).thenReturn(mockList);
        assertEquals(mockList, service.getByParameters(null, null, null));
    }

    ///Auxiliary methods

    private BookDTO createDTO() {
        final BookDTO bookDTO = new BookDTO(ID, TITLE, AUTHOR_NAME, GENRE, COPIES, COPIES, false);
        return bookDTO;
    }

    private List<BookDTO> createList() {
        final List<BookDTO> bookDTOList = new ArrayList<>();
        bookDTOList.add(createDTO());
        return bookDTOList;
    }

    private Author createAuthor() {
        return new Author(ID, AUTHOR_NAME, false);
    }
}