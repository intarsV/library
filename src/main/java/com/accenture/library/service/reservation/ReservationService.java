package com.accenture.library.service.reservation;

import com.accenture.library.dto.ReservationDTO;

import java.util.List;

public interface ReservationService {
    ReservationDTO makeReservation(Long bookId, String userName);

    Long deleteReservation(Long id, String userName);

    Long handOut(Long reservationId);

    Long takeIn(Long reservationId);

    List<ReservationDTO> getByParameters(String bookTitle, String userName, Boolean handOut, Boolean returned);

    List<ReservationDTO> getReservationQueue();

    Long deleteUserReservation(Long reservationId);
}
