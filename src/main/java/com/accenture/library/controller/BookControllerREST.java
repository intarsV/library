package com.accenture.library.controller;

import com.accenture.library.domain.Book;
import com.accenture.library.dto.AuthorDto;
import com.accenture.library.dto.BookDto;
import com.accenture.library.dto.GenreDto;
import com.accenture.library.service.bookSrv.BookSrv;
import com.accenture.library.service.bookSrv.BookSrvImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/search/authors")
    public List<Book> getByAuthor(@RequestBody AuthorDto author) throws Exception {
        return bookSrv.getByAuthor(author.getName());
    }

    @GetMapping("/search/genres")
    public List<Book> getByGenre(@RequestBody GenreDto genre) {
        return bookSrv.getByGenre(genre.getGenre());
    }

    @PostMapping
    public ResponseEntity<BookDto> saveBook(@RequestBody BookDto bookDto) throws Exception {
        Long id = bookSrv.save(bookDto.getTitle(), bookDto.getAuthorName(), bookDto.getGenre(), bookDto.getCopies());
        bookDto.setId(id);
        return new ResponseEntity<>(bookDto, HttpStatus.CREATED);
    }
}
