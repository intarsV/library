package com.accenture.library.controller;

import com.accenture.library.dto.BookDTO;
import com.accenture.library.service.book.BookService;
import com.accenture.library.service.book.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookControllerREST {

    private BookService bookService;

    @Autowired
    public BookControllerREST(BookServiceImpl bookSrv) {
        this.bookService = bookSrv;
    }


    @GetMapping
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping(value = "/search")
    public List<BookDTO> getByParameters(@RequestBody BookDTO bookDTO) {
        final String title = bookDTO.getTitle();
        final String author = bookDTO.getAuthorName();
        final String genre = bookDTO.getGenre();
        return bookService.getByParameters(title, author, genre);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<BookDTO> saveBook(@RequestBody BookDTO bookDto) throws Exception {
        Long id = bookService.addBook(bookDto.getTitle(), bookDto.getAuthorName(), bookDto.getGenre(), bookDto.getCopies());
        bookDto.setId(id);
        return new ResponseEntity<>(bookDto, HttpStatus.CREATED);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<BookDTO> deleteBook(@RequestBody BookDTO bookDto) {
        final boolean isDeleted = bookService.deleteBook(bookDto.getId());
        bookDto.setDeleted(isDeleted);
        return new ResponseEntity<>(bookDto, HttpStatus.ACCEPTED);
    }
}
