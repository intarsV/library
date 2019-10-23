package com.accenture.library.service.book;

import com.accenture.library.dto.BookDTO;

import java.util.List;

public interface BookService {
    List<BookDTO> getAllBooks();

    Long addBook(String title, String author, String genre, int copies) throws Exception;

    Boolean deleteBook(Long id);

    List<BookDTO> getByParameters(String title, String author, String genre);
}
