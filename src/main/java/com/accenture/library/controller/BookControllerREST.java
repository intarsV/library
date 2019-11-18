package com.accenture.library.controller;

import com.accenture.library.dto.BookDTO;
import com.accenture.library.service.book.BookService;
import com.accenture.library.service.book.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@CrossOrigin(origins = "http://localhost:3000")  //should remove on production
public class BookControllerREST {

    private BookService bookService;

    @Autowired
    public BookControllerREST(BookServiceImpl bookSrv) {
        this.bookService = bookSrv;
    }

    @GetMapping
    public List<BookDTO> getByParameters(final BookDTO bookDTO) {
        final String title = bookDTO.getTitle();
        final String authorName = bookDTO.getAuthorName();
        final String genre = bookDTO.getGenre();
        return bookService.getByParameters(title, authorName, genre);
    }

    @PostMapping
    public ResponseEntity<BookDTO> saveBook(@Valid @RequestBody final BookDTO bookDto) {
        return new ResponseEntity<>(bookService.addBook(bookDto), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BookDTO> disableBook(@PathVariable Long id, @RequestBody final BookDTO bookDto) {
        bookDto.setEnabled(bookService.disableBook(id));
        return new ResponseEntity<>(bookDto, HttpStatus.ACCEPTED);
    }
}
