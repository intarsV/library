package com.accenture.library.service.book;

import com.accenture.library.domain.Author;
import com.accenture.library.domain.Book;
import com.accenture.library.dto.BookDTO;
import com.accenture.library.exceptions.LibraryException;
import com.accenture.library.repository.BookRepository;
import com.accenture.library.service.author.AuthorService;
import com.accenture.library.service.author.AuthorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private AuthorService authorService;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorServiceImpl authorSrv) {
        this.bookRepository = bookRepository;
        this.authorService = authorSrv;
    }

    @Override
    public Long addBook(String title, String authorName, String genre, int copies) {
        if (StringUtils.isEmpty(title) || StringUtils.isEmpty(authorName) || StringUtils.isEmpty(genre) || copies == 0) {  //dto validation
            throw new LibraryException("Bad request - require all field");
        }
        Optional<Book> foundBook = bookRepository.findByTitleAndAuthor_Name(title, authorName);
        if (foundBook.isPresent()) {
            throw new LibraryException("Duplicate book exists!");
        }
        final Author bookAuthor = authorService.findByName(authorName);
        final Book book = new Book(title, bookAuthor, genre, copies, copies, true);
        return bookRepository.save(book).getId();
    }

    @Override
    public Boolean disableBook(Long id) {
        final Optional<Book> findBook = bookRepository.findById(id);
        if (!findBook.isPresent()) {
            throw new LibraryException("No such book found");
        }
        Book updateBook = findBook.get();
        updateBook.setEnabled(false);
        bookRepository.save(updateBook);
        return updateBook.isEnabled();
    }

    @Override
    public List<BookDTO> getByParameters(String title, String author, String genre) {
        if (StringUtils.isEmpty(title) && StringUtils.isEmpty(author) && StringUtils.isEmpty(genre)) {
            return new ArrayList<>();
        }
        return bookRepository.findByParameters(title, author, genre);
    }
}