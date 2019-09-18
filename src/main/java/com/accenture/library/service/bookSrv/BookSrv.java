package com.accenture.library.service.bookSrv;

import com.accenture.library.domain.Book;
import com.accenture.library.dto.SearchRequestDTO;

import java.util.List;

public interface BookSrv {
    List<Book> getAllBooks();

    Long addBook(String title, String author, String genre, Integer copies) throws Exception;

    List<Book> getByParameters(String title, String author, String genre);

//    List<Book> getByAuthor(String author) throws Exception;
//
//    List<Book> getByGenre(String genre);
}
