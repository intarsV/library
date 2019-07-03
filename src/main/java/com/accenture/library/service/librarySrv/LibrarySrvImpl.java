package com.accenture.library.service.librarySrv;

import com.accenture.library.domain.Library;
import com.accenture.library.repository.BookRepository;
import com.accenture.library.repository.LibraryRepository;
import com.accenture.library.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibrarySrvImpl implements LibrarySrv {

    private BookRepository bookRepository;
    private LibraryRepository libraryRepository;

    public LibrarySrvImpl(BookRepository bookRepository, LibraryRepository libraryRepository) {
        this.bookRepository = bookRepository;
        this.libraryRepository = libraryRepository;
    }

    @Autowired


    @Override
    public List<Library> reservedBookCount() {
        List<Library> cool=libraryRepository.findDistinc();


        for (int i=0;i<cool.size();i++) {
            System.out.println(cool.get(i).getBook().getTitle()+cool.get(i).getAvailable());
        }
        return cool;
    }
}
