package com.accenture.library.controller;

import com.accenture.library.domain.Author;
import com.accenture.library.dto.AuthorDTO;
import com.accenture.library.service.authorSrv.AuthorSrv;
import com.accenture.library.service.authorSrv.AuthorSrvImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/authors")
public class AuthorControllerREST {

    private AuthorSrv authorSrv;

    @Autowired
    public AuthorControllerREST(AuthorSrvImpl authorService) {
        this.authorSrv = authorService;
    }

    @GetMapping
    public List<Author> getAllAuthors() {
        return authorSrv.authorList();
    }

    @GetMapping("/search")
    public Author getByName(@RequestBody AuthorDTO name) throws Exception {
        return authorSrv.findByName(name.getName());
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> saveAuthor(@RequestBody AuthorDTO authorDto) {
        final Long id = authorSrv.saveAuthor(authorDto.getName());
        authorDto.setId(id);
        return new ResponseEntity<>(authorDto, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<AuthorDTO> deleteAuthor(@RequestBody AuthorDTO authorDto) {
        final boolean isDeleted = authorSrv.deleteAuthor(authorDto.getId());
        authorDto.setDeleted(isDeleted);
        return new ResponseEntity<>(authorDto, HttpStatus.ACCEPTED);
    }
}
