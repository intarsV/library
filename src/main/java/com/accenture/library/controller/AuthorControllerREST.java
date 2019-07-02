package com.accenture.library.controller;

import com.accenture.library.domain.Author;
import com.accenture.library.dto.AuthorDto;
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

    @GetMapping("/search/name")
    public Author getByName(@RequestBody AuthorDto authorName) {
        return authorSrv.findByName(authorName.getName());
    }

    @PostMapping
    public ResponseEntity<AuthorDto> saveAuthor(@RequestBody AuthorDto authorDto) {
        Long id = authorSrv.saveAuthor(authorDto.getName());
        authorDto.setId(id);
        return new ResponseEntity<>(authorDto, HttpStatus.CREATED);
    }
}
