package com.accenture.library.controller;

import com.accenture.library.domain.Reservation;
import com.accenture.library.dto.ReservationDto;
import com.accenture.library.service.bookReservation.ReservationSvr;
import com.accenture.library.service.bookReservation.ReservationSvrImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reservation")
public class ReservationControllerREST {

    private ReservationSvr reservationSvr;

    @Autowired
    public ReservationControllerREST(ReservationSvrImpl bookReservationSvr) {
        this.reservationSvr = bookReservationSvr;
    }

    @GetMapping
    public List<Reservation> getReservations() {
        return reservationSvr.getAllReservations();
    }

    @GetMapping(value = "/search/users")
    public List<Reservation> getReservationsByUser(@RequestBody ReservationDto reservationDto) {
        return reservationSvr.getAllByUser(reservationDto.getUserId());
    }

    @GetMapping(value = "/search/books")
    public List<Reservation> getReservationsByBook(@RequestBody ReservationDto reservationDto) {
        return reservationSvr.getAllByBook(reservationDto.getBookId());
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ReservationDto reservationDto) {
        Date reservationDate = new Date();
        Long id = reservationSvr.save(reservationDto.getBookId(), reservationDto.getUserId(), reservationDate);
        reservationDto.setBookId(id);
        reservationDto.setReservationDate(reservationDate);
        return new ResponseEntity(reservationDto, HttpStatus.CREATED);
    }
}
