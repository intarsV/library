package com.accenture.library.service.bookSrv;

import com.accenture.library.domain.Author;
import com.accenture.library.domain.Book;
import com.accenture.library.dto.SearchRequestDTO;
import com.accenture.library.repository.BookRepository;
import com.accenture.library.service.authorSrv.AuthorSrv;
import com.accenture.library.service.authorSrv.AuthorSrvImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookSrvImpl implements BookSrv {

    private BookRepository repository;
    private AuthorSrv authorSrv;

    @Autowired
    public BookSrvImpl(BookRepository repository, AuthorSrvImpl authorSrv) {
        this.repository = repository;
        this.authorSrv = authorSrv;
    }

    @Override
    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    @Override
    public Long addBook(String title, String authorName, String genre, Integer copies) {
        Author bookAuthor = authorSrv.findByName(authorName);
        Book book = new Book(title, bookAuthor, genre, copies, copies);
        return repository.save(book).getId();
    }

    @Override
    public List<Book> getByParameters(String title, String author, String genre) {
        return repository.findParameters(title, author, genre);
    }
}
