package com.accenture.library.service.book;

import com.accenture.library.dto.BookDTO;

import java.util.List;

public interface BookService {

    BookDTO addBook(BookDTO bookDto);

    Boolean disableBook(Long id);

    List<BookDTO> getByParameters(String title, String author, String genre);
}
