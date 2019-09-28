package com.accenture.library.controller;

import com.accenture.library.domain.Reservation;
import com.accenture.library.dto.ReservationDTO;
import com.accenture.library.service.reservationSrv.ReservationSrv;
import com.accenture.library.service.reservationSrv.ReservationSrvImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationControllerREST {

    private ReservationSrv reservationService;

    @Autowired
    public ReservationControllerREST(ReservationSrvImpl bookReservationSvr) {
        this.reservationService = bookReservationSvr;
    }

    @PostMapping(value="/user/list-reservation")
    public List<ReservationDTO> getReservations(@RequestBody ReservationDTO reservationDTO, Authentication authentication){
        System.out.println("this is called");
        final String userName = authentication.getName();
        final String bookTitle = reservationDTO.getBookTitle();
        final Boolean returned = reservationDTO.isReturned();
        System.out.println("Request: "+bookTitle+" "+userName+" "+returned);
        return reservationService.getByParameters( bookTitle, userName, returned);
    }

    //User make book reservation
    @PostMapping(value="/user/make-reservation")
    public ResponseEntity makeReservation(@RequestBody ReservationDTO reservationDto, Authentication authentication) {
        final String userName = authentication.getName();
        Long id = reservationService.makeReservation(reservationDto.getBookId(), userName);
        reservationDto.setId(id);
        return new ResponseEntity<>(reservationDto, HttpStatus.CREATED);
    }

    //Library ADMIN reservation queue
    @GetMapping(value = "/admin/queue")
    public List<ReservationDTO> getQueue() {
        return reservationService.getReservationQueue();
    }

    //Library ADMIN hands out the book
    @PutMapping(value = "/admin/hand-out")
    public ResponseEntity handOut(@RequestBody ReservationDTO reservationDto) {
        reservationService.handOut(reservationDto.getId());
        return new ResponseEntity<>(reservationDto, HttpStatus.CREATED);
    }

    //Library ADMIN take in the book
    @PutMapping(value = "/admin/take-in")
    public ResponseEntity takeIn(@RequestBody Reservation reservationDto) {
        reservationService.takeIn(reservationDto.getId());
        return new ResponseEntity<>(reservationDto, HttpStatus.OK);
    }

    //Library ADMIN search by parameters
    @GetMapping(value = "/admin/search")
    public List<ReservationDTO> searchByParameters(@RequestBody ReservationDTO reservationDTO) {
        final String bookTitle = reservationDTO.getBookTitle();
        final String userName = reservationDTO.getUserName();
        final Boolean returned = reservationDTO.isReturned();
        return reservationService.getByParameters(bookTitle, userName, returned);
    }
}
