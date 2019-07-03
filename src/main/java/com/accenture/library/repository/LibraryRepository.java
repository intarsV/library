package com.accenture.library.repository;

import com.accenture.library.domain.Book;
import com.accenture.library.domain.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LibraryRepository extends JpaRepository<Library,Long > {

    @Query(nativeQuery = true, value = "SELECT BOOK.ID, (CASE WHEN RESERVATION.BOOK IS NULL  THEN BOOK.ID ELSE RESERVATION.BOOK END)  AS BOOK, Book.COPIES, SUM(CASE WHEN RETURNED=0 THEN 1 ELSE 0 END) AS RESERVED\n" +
            ", (CASE WHEN (BOOK.COPIES-SUM(RESERVATION.BOOK)) IS NULL THEN BOOK.COPIES ELSE (BOOK.COPIES-SUM(CASE WHEN RETURNED=0 THEN 1 ELSE 0 END)) END) AS AVAILABLE\n" +
            "FROM BOOK LEFT JOIN RESERVATION on BOOK.ID=RESERVATION.BOOK \n" +
            "GROUP BY BOOK.ID; ")
    List<Library> findDistinc();
}
