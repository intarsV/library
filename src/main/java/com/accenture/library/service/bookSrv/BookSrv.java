package com.accenture.library.service.bookSrv;

import com.accenture.library.domain.Book;
import com.accenture.library.dto.BookDTO;

import java.util.List;

public interface BookSrv {
    List<BookDTO> getAllBooks();

    Long addBook(String title, String author, String genre, int copies) throws Exception;

    Boolean deleteBook(Long id);

    List<BookDTO> getByParameters(String title, String author, String genre);
}
