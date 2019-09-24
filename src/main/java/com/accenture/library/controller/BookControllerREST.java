package com.accenture.library.controller;

import com.accenture.library.dto.BookDTO;
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
    public List<BookDTO> getAllBooks() {
        return bookSrv.getAllBooks();
    }

    @PostMapping(value = "/search")
    public List<BookDTO> getByParameters(@RequestBody BookDTO bookDTO) {
        final String title = bookDTO.getTitle();
        final String author = bookDTO.getAuthorName();
        final String genre = bookDTO.getGenre();
        return bookSrv.getByParameters(title, author, genre);
    }

    @PostMapping
    public ResponseEntity<BookDTO> saveBook(@RequestBody BookDTO bookDto) throws Exception {
        Long id = bookSrv.addBook(bookDto.getTitle(), bookDto.getAuthorName(), bookDto.getGenre(), bookDto.getCopies());
        bookDto.setId(id);
        return new ResponseEntity<>(bookDto, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<BookDTO> deleteBook(@RequestBody BookDTO bookDto) {
        final boolean isDeleted = bookSrv.deleteBook(bookDto.getId());
        bookDto.setDeleted(isDeleted);
        return new ResponseEntity<>(bookDto, HttpStatus.ACCEPTED);
    }
}
