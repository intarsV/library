package com.accenture.library.service.bookSrv;

import com.accenture.library.domain.Author;
import com.accenture.library.domain.Book;
import com.accenture.library.dto.BookDTO;
import com.accenture.library.exceptions.LibraryException;
import com.accenture.library.repository.BookRepository;
import com.accenture.library.service.authorSrv.AuthorSrv;
import com.accenture.library.service.authorSrv.AuthorSrvImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookSrvImpl implements BookSrv {

    private BookRepository bookRepository;
    private AuthorSrv authorSrv;

    @Autowired
    public BookSrvImpl(BookRepository bookRepository, AuthorSrvImpl authorSrv) {
        this.bookRepository = bookRepository;
        this.authorSrv = authorSrv;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.getAllBooks();
     }

    @Override
    public Long addBook(String title, String authorName, String genre, int copies) {
        if (StringUtils.isEmpty(title) || StringUtils.isEmpty(authorName) || StringUtils.isEmpty(genre) || copies == 0) {
            throw new LibraryException("Bad request - require all field");
        }
        final Author bookAuthor = authorSrv.findByName(authorName);
        final Book book = new Book(title, bookAuthor, genre, copies, copies, false);
        return bookRepository.save(book).getId();
    }

    @Override
    public Boolean deleteBook(Long id) {
        final Optional<Book> findBook = bookRepository.findById(id);
        if (!findBook.isPresent()) {
            throw new LibraryException("Book with ID: " + id + "not found");
        } else {
            bookRepository.delete(findBook.get());
        }
        return true;
    }

    @Override
    public List<BookDTO> getByParameters(String title, String author, String genre) {
        if (StringUtils.isEmpty(title) && StringUtils.isEmpty(author) && StringUtils.isEmpty(genre)) {
            return new ArrayList<>();
        }
        return bookRepository.findByParameters(title, author, genre);
    }
}
