package com.accenture.library.service.book;

import com.accenture.library.domain.Author;
import com.accenture.library.domain.Book;
import com.accenture.library.dto.BookDTO;
import com.accenture.library.exceptions.LibraryException;
import com.accenture.library.repository.BookRepository;
import com.accenture.library.service.author.AuthorService;
import com.accenture.library.service.author.AuthorServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private static final String DATABASE_SAVE_ERROR = "Database save error";
    private BookRepository bookRepository;
    private AuthorService authorService;
    private Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorServiceImpl authorSrv) {
        this.bookRepository = bookRepository;
        this.authorService = authorSrv;
    }

    @Override
    public BookDTO addBook(BookDTO bookDTO) {
        final String title = bookDTO.getTitle();
        final String authorName = bookDTO.getAuthorName();
        final String genre = bookDTO.getGenre();
        final int copies = bookDTO.getCopies();
        validateRequestData(title, authorName, genre, copies);
        validateRequestForDuplicates(title, authorName);
        final Author bookAuthor = authorService.findByName(authorName);
        final Book book = new Book(title, bookAuthor, genre, copies, copies, true);
        try {
            bookDTO.setId(bookRepository.save(book).getId());
        } catch (Exception e) {
            logger.error(DATABASE_SAVE_ERROR, e);
            throw new LibraryException(DATABASE_SAVE_ERROR);
        }
        bookDTO.setAvailable(copies);
        bookDTO.setEnabled(true);
        return bookDTO;
    }

    @Override
    public Boolean disableBook(Long id) {
        final Optional<Book> foundBook = bookRepository.findById(id);
        if (!foundBook.isPresent()) {
            throw new LibraryException("No such book found");
        }
        Book updateBook = foundBook.get();
        updateBook.setEnabled(false);
        try {
            bookRepository.save(updateBook);
        } catch (Exception e) {
            logger.error(DATABASE_SAVE_ERROR, e);
            throw new LibraryException(DATABASE_SAVE_ERROR);
        }
        return updateBook.isEnabled();
    }

    @Override
    public List<BookDTO> getByParameters(String title, String author, String genre) {
        if (StringUtils.isEmpty(title) && StringUtils.isEmpty(author) && StringUtils.isEmpty(genre)) {
            return bookRepository.getAllBooks();
        }
        return bookRepository.findByParameters(title, author, genre);
    }

    //AUXILIARY METHODS

    private void validateRequestData(String title, String authorName, String genre, int copies) {
        if (StringUtils.isEmpty(title) || StringUtils.isEmpty(authorName)
                || StringUtils.isEmpty(genre) || copies == 0) {
            throw new LibraryException("Bad request - require all field");
        }
    }

    private void validateRequestForDuplicates(String title, String authorName) {
        Optional<Book> foundBook = bookRepository.findByTitleAndAuthor_Name(title, authorName);
        if (foundBook.isPresent()) {
            throw new LibraryException("Duplicate book exists!");
        }
    }
}
