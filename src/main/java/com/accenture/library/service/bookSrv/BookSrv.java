package com.accenture.library.service.bookSrv;

import com.accenture.library.domain.Book;

import java.util.List;

public interface BookSrv {
    List<Book> getAllBooks();

    Long save(String title, String author, String genre, Integer copies) throws Exception;

    List<Book> getByAuthor(String author) throws Exception;

    List<Book> getByGenre(String genre);
}
