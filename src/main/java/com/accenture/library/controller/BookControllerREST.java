package com.accenture.library.controller;

import com.accenture.library.domain.Book;
import com.accenture.library.dto.AuthorDto;
import com.accenture.library.dto.BookDto;
import com.accenture.library.dto.GenreDto;
import com.accenture.library.dto.SearchRequestDTO;
import com.accenture.library.service.bookSrv.BookSrv;
import com.accenture.library.service.bookSrv.BookSrvImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


//@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
@RequestMapping("/api/v1/books")
public class BookControllerREST {

    private BookSrv bookSrv;

    @Autowired
    public BookControllerREST(BookSrvImpl bookSrv) {
        this.bookSrv = bookSrv;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookSrv.getAllBooks();
    }

    @PostMapping(value = "/search")
    public List<Book> getByParameters(@RequestBody @Validated SearchRequestDTO searchRequestDTO) {
        final String title = searchRequestDTO.getTitle();
        final String author = searchRequestDTO.getAuthor();
        final String genre = searchRequestDTO.getGenre();
        return bookSrv.getByParameters(title, author, genre);
    }

    @PostMapping
    public ResponseEntity<BookDto> saveBook(@RequestBody BookDto bookDto) throws Exception {
        Long id = bookSrv.addBook(bookDto.getTitle(), bookDto.getAuthorName(), bookDto.getGenre(), bookDto.getCopies());
        bookDto.setId(id);
        return new ResponseEntity<>(bookDto, HttpStatus.CREATED);
    }
}
