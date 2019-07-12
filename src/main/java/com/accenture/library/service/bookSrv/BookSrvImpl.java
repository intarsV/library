package com.accenture.library.service.bookSrv;

import com.accenture.library.domain.Author;
import com.accenture.library.domain.Book;
import com.accenture.library.domain.EnumGenre;
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
    public Long save(String title, String authorName, String genre, Integer copies) {
        Author bookAuthor = authorSrv.findByName(authorName);
        Book book = new Book(title, bookAuthor, EnumGenre.valueOf(genre), copies,copies);
        return repository.save(book).getId();
    }

    @Override
    public List<Book> getByAuthor(String author) {
        Author foundAuthor = authorSrv.findByName(author);
        return repository.findByAuthor(foundAuthor);
    }

    @Override
    public List<Book> getByGenre(String genre) {
        return repository.findByGenre(EnumGenre.valueOf(genre));
    }
}
