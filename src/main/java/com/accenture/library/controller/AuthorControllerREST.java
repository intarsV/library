package com.accenture.library.controller;

import com.accenture.library.dto.AuthorDTO;
import com.accenture.library.service.author.AuthorService;
import com.accenture.library.service.author.AuthorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorControllerREST {

    private AuthorService authorService;

    @Autowired
    public AuthorControllerREST(AuthorServiceImpl authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<AuthorDTO> getAllAuthors() {
        return authorService.authorList();
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> saveAuthor(@RequestBody AuthorDTO authorDto) {
        final Long id = authorService.saveAuthor(authorDto.getName());
        authorDto.setId(id);
        return new ResponseEntity<>(authorDto, HttpStatus.CREATED);
    }

    @PostMapping(value="/delete")
    public ResponseEntity<AuthorDTO> deleteAuthor(@RequestBody AuthorDTO authorDto) {
        final boolean isDeleted = authorService.deleteAuthor(authorDto.getId());
        authorDto.setDeleted(isDeleted);
        return new ResponseEntity<>(authorDto, HttpStatus.ACCEPTED);
    }
}
