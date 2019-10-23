package com.accenture.library.controller;

import com.accenture.library.dto.ReservationDTO;
import com.accenture.library.service.reservation.ReservationService;
import com.accenture.library.service.reservation.ReservationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationControllerREST {

    private ReservationService reservationService;

    @Autowired
    public ReservationControllerREST(ReservationServiceImpl bookReservationSvr) {
        this.reservationService = bookReservationSvr;
    }

    //Library USER reservations
    @PostMapping(value = "/user/queue")
    public List<ReservationDTO> getReservations(@RequestBody ReservationDTO reservationDTO, Authentication authentication) {
        final String userName = authentication.getName();
        final String bookTitle = reservationDTO.getBookTitle();
        final Boolean handOut = reservationDTO.isHandOut();
        final Boolean returned = reservationDTO.isReturned();
        return reservationService.getByParameters(bookTitle, userName, handOut, returned);
    }

    //Library USER remove reservation
    @PostMapping(value = "/user/remove-reservation")
    public ResponseEntity removeReservation(@RequestBody ReservationDTO reservationDto, Authentication authentication) {
        final Long reservationId=reservationDto.getId();
        final String userName = authentication.getName();
        return new ResponseEntity<>(reservationService.deleteReservation(reservationId, userName), HttpStatus.ACCEPTED);
    }

    //Library USER make book reservation
    @PostMapping(value = "/user/make-reservation")
    public ResponseEntity makeReservation(@RequestBody ReservationDTO reservationDto, Authentication authentication) {
        final String userName = authentication.getName();
        return new ResponseEntity<>(reservationService.makeReservation(reservationDto.getBookId(),
                                                                    userName), HttpStatus.CREATED);
    }

    //Library ADMIN reservation queue
    @GetMapping(value = "/admin/queue")
    public List<ReservationDTO> getQueue() {
        return reservationService.getReservationQueue();
    }

    //Library ADMIN hands out the book
    @PostMapping(value = "/admin/hand-out")
    public ResponseEntity handOut(@RequestBody ReservationDTO reservationDto) {
        reservationService.handOut(reservationDto.getId());
        return new ResponseEntity<>(reservationDto, HttpStatus.CREATED);
    }

    //Library ADMIN take in the book
    @PostMapping(value = "/admin/take-in")
    public ResponseEntity takeIn(@RequestBody ReservationDTO reservationDto) {
        reservationService.takeIn(reservationDto.getId());
        return new ResponseEntity<>(reservationDto, HttpStatus.OK);
    }

    //Library ADMIN search by parameters
    @PostMapping(value = "/admin/search")
    public List<ReservationDTO> searchByParameters(@RequestBody ReservationDTO reservationDTO) {
        final String bookTitle = reservationDTO.getBookTitle();
        final String userName = reservationDTO.getUserName();
        final Boolean handOut = reservationDTO.isHandOut();
        final Boolean returned = reservationDTO.isReturned();
        return reservationService.getByParameters(bookTitle, userName, handOut, returned);
    }
}
