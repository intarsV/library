package com.accenture.library.service.reservationSrv;

import com.accenture.library.domain.Reservation;

import java.util.Date;
import java.util.List;

public interface ReservationSrv {
    Long save(Long bookId, Long userId, Date date);
    Long update(Long reservationId);
    List<Reservation> getAllReservations();
    List<Reservation> getAllByBook(Long bookId);
    List<Reservation> getAllByUser(Long userId);
}
