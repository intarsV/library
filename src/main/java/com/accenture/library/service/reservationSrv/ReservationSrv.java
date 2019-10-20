package com.accenture.library.service.reservationSrv;

import com.accenture.library.dto.ReservationDTO;

import java.util.List;

public interface ReservationSrv {
    ReservationDTO makeReservation(Long bookId, String userName);

    Long handOut(Long reservationId);

    Long takeIn(Long reservationId);

    List<ReservationDTO> getByParameters(String bookTitle, String userName, Boolean handOut, Boolean returned);

    List<ReservationDTO> getReservationQueue();
}
