package com.accenture.library.repository;

import com.accenture.library.domain.Reservation;
import com.accenture.library.dto.ReservationDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT new com.accenture.library.dto.ReservationDTO(r.id, r.book.id, r.book.title, r.user.id" +
            ", r.user.userName, r.reservationDate, r.handOut, r.returned, r.deleted) " +
            "FROM Reservation r WHERE  (:bookTitle IS NULL OR LOWER( r.book.title) LIKE %:bookTitle%)" +
            " AND (:usrName IS NULL OR LOWER( r.user.userName) LIKE %:usrName%) " +
            "AND (:handOut IS NULL OR r.handOut=:handOut) AND(:returned IS NULL OR r.returned=:returned) AND (r.deleted=0)")
    List<ReservationDTO> getByParameters(@Param("bookTitle") String bookTitle,
                                         @Param("usrName") String userName,
                                         @Param("handOut")Boolean handOut,
                                         @Param("returned") Boolean returned);

    @Query("SELECT new com.accenture.library.dto.ReservationDTO(r.id, r.book.id, r.book.title, r.user.id" +
            ", r.user.userName, r.reservationDate, r.handOut, r.returned, r.deleted) " +
            "FROM Reservation r WHERE r.handOut=false AND r.returned=false")
    List<ReservationDTO> getQueue();
}
