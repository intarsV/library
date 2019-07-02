package com.accenture.library.service.librarySrv;

import com.accenture.library.domain.Book;
import com.accenture.library.repository.BookRepository;
import com.accenture.library.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LibrarySrvImpl implements LibrarySrv {

    private BookRepository bookRepository;
    private ReservationRepository reservationRepository;

    @Autowired
    public LibrarySrvImpl(BookRepository bookRepository, ReservationRepository reservationRepository) {
        this.bookRepository = bookRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Map<Book, Integer> reservedBookCount() {
//        Map<Book, Integer> myMap = reservationRepository.findAllByReturned(false).stream()
//                .collect(Collectors.groupingBy(
//                        Function.identity(),
//                       HashMap::new,
//                        Collectors.counting()));
//
//        for (Map.Entry<Book, Integer> entry : myMap.entrySet()) {
//            System.out.println(entry.getKey().getTitle() + "/" + entry.getValue());
//        }
        return null;
    }
}
