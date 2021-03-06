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
            ", r.user.userName, r.reservationDate, r.status) " +
            "FROM Reservation r WHERE (:bookTitle IS NULL OR LOWER( r.book.title) LIKE LOWER(concat('%',:bookTitle,'%'))) " +
            "AND (:userName IS NULL OR LOWER( r.user.userName) LIKE LOWER(concat('%',:userName,'%'))) " +
            "AND (:status IS NULL OR r.status=:status)")
    List<ReservationDTO> getByParameters(@Param("bookTitle") String bookTitle,
                                         @Param("userName") String userName,
                                         @Param("status") String status);

}
