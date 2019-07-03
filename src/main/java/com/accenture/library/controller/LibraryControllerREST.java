package com.accenture.library.controller;

import com.accenture.library.domain.Library;
import com.accenture.library.service.librarySrv.LibrarySrv;
import com.accenture.library.service.librarySrv.LibrarySrvImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/library")
public class LibraryControllerREST {

    private LibrarySrv librarySrv;

    public LibraryControllerREST(LibrarySrvImpl librarySrv) {
        this.librarySrv = librarySrv;
    }

    @GetMapping
    public List<Library> getBok(){
        return librarySrv.reservedBookCount();
    }
}
