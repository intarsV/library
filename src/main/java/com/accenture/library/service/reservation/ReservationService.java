package com.accenture.library.service.reservation;

import com.accenture.library.dto.ReservationDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReservationService {

    ReservationDTO makeReservation(Long bookId, String userName);

    List<ReservationDTO> getByParameters(String bookTitle, String userName, String status, boolean isAdmin);

    @Transactional
    Long updateStatus(Long reservationId, String userName, String status, boolean isAdmin);
}
