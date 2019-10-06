package com.accenture.library.repository;

import com.accenture.library.domain.Book;
import com.accenture.library.dto.BookDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT new com.accenture.library.dto.BookDTO(b.id, b.title, b.author.name, b.genre, b.copies, b.available, b.deleted) FROM Book b WHERE b.deleted=false")
    List<BookDTO> getAllBooks();

    @Query("SELECT new com.accenture.library.dto.BookDTO(b.id, b.title, b.author.name, b.genre, b.copies, b.available, b.deleted)" +
            " FROM Book b WHERE (:title IS NULL OR LOWER( b.title) LIKE %:title%) " +
            "AND (:author IS NULL OR LOWER( b.author.name) LIKE %:author%) " +
            "AND (:genre IS NULL OR b.genre=:genre) AND b.deleted=false")
    List<BookDTO> findByParameters(@Param("title") String title,
                                   @Param("author") String author,
                                   @Param("genre") String genre);


}
