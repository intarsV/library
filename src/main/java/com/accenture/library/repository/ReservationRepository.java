package com.accenture.library.repository;

import com.accenture.library.domain.Book;
import com.accenture.library.domain.Reservation;
import com.accenture.library.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByReturned(boolean returned);

    List<Reservation> findByBook(Book book);

    List<Reservation> findByUser(User user);

//    @Query(nativeQuery = true, value = "SELECT book, SUM( book) AS Count FROM RESERVATION where returned=0 group By book")
//    Map<Book, Integer> findDistinc();
}
