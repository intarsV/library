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

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceImplTest {

    private static final Long ID=1L;
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

//    @Test
//    public void getAllBooks() {
//        final List<BookDTO> mockList = createList();
//        when(bookRepository.getAllBooks()).thenReturn(mockList);
//        Assert.assertEquals(mockList, service.getAllBooks());
//    }

    @Test
    public void shouldSaveBook() {
        Author author = createAuthor();
        Book book = new Book( TITLE, author, GENRE, COPIES, COPIES, false);
        book.setId(ID);
        when(authorSrv.findByName(AUTHOR_NAME)).thenReturn(author);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        assertEquals(ID, service.addBook(TITLE, AUTHOR_NAME, GENRE, COPIES));
    }

    @Test
    public void shouldThrowErrorOnSaveIsParamMissing(){
        String title="";
        int copies = 0;
        exception.expect(LibraryException.class);
        exception.expectMessage("Bad request - require all field");
        service.addBook(title, AUTHOR_NAME,null, copies);
    }

    @Test
    public void shouldDeleteBook() {
        final Author author = createAuthor();
        Book book = new Book( TITLE, author, GENRE, COPIES, COPIES, false);
        when(bookRepository.findById(ID)).thenReturn(Optional.of(book));
        assertTrue(service.disableBook(ID));
    }

    @Test
    public void shouldThrowErrorOnDelete(){
        when(bookRepository.findById(ID)).thenReturn(Optional.empty());
        exception.expect(LibraryException.class);
        exception.expectMessage("Book with ID: " + ID + "not found");
        service.disableBook(ID);
    }

    @Test
    public void shouldReturnListOnParameterSearch() {
        final List<BookDTO> mockList = createList();
        when(bookRepository.findByParameters(null, AUTHOR_NAME, null)).thenReturn(mockList);
        Assert.assertEquals(mockList, service.getByParameters(null, AUTHOR_NAME, null));
    }

    @Test
    public void shouldReturnEmptyListOnParameterSearchWithoutAnyValidParam() {
        Assert.assertEquals(new ArrayList<>(), service.getByParameters(null, null, null));
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
        return new Author( ID, AUTHOR_NAME, false);
    }
}