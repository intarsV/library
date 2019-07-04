package com.accenture.library.service.librarySrv;

import com.accenture.library.domain.Library;
import com.accenture.library.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibrarySrvImpl implements LibrarySrv {

    private LibraryRepository libraryRepository;

    @Autowired
    public LibrarySrvImpl(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    @Override
    public List<Library> reservedBookCount() {
        return libraryRepository.getBookAvailability();
    }
}
