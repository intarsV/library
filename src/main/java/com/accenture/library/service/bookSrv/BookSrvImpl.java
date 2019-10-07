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
        Author bookAuthor = authorSrv.findByName(authorName);
        Book book = new Book(title, bookAuthor, genre, copies, copies, false);
        return bookRepository.save(book).getId();
    }

    @Override
    public Boolean deleteBook(Long id) {
        Optional<Book> findBook = bookRepository.findById(id);
        if (!findBook.isPresent()) {
            throw new LibraryException("Book with ID: " + id + "not found");
        } else {
            bookRepository.delete(findBook.get());
        }
        return true;
    }

    @Override
    public List<BookDTO> getByParameters(String title, String author, String genre) {
        return bookRepository.findByParameters(title, author, genre);
    }
}
