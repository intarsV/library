package com.accenture.library.service.bookReservation;

import com.accenture.library.domain.Reservation;

import java.util.Date;
import java.util.List;

public interface ReservationSvr {
    Long save(Long bookId, Long userId, Date date);
    List<Reservation> getAllReservations();
    List<Reservation> getAllByBook(Long bookId);
    List<Reservation> getAllByUser(Long userId);
}
