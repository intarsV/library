package com.accenture.library.controller;

import com.accenture.library.domain.Reservation;
import com.accenture.library.dto.ReservationDto;
import com.accenture.library.service.bookReservation.ReservationSrv;
import com.accenture.library.service.bookReservation.ReservationSrvImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/api/v1/reservation")
public class ReservationControllerREST {

    private ReservationSrv reservationService;

    @Autowired
    public ReservationControllerREST(ReservationSrvImpl bookReservationSvr) {
        this.reservationService = bookReservationSvr;
    }

    @GetMapping
    public List<Reservation> getReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping(value = "/search/users")
    public List<Reservation> getReservationsByUser(@RequestBody ReservationDto reservationDto) {
        return reservationService.getAllByUser(reservationDto.getUserId());
    }

    @GetMapping(value = "/search/books")
    public List<Reservation> getReservationsByBook(@RequestBody ReservationDto reservationDto) {
        return reservationService.getAllByBook(reservationDto.getBookId());
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ReservationDto reservationDto) {
        Date reservationDate = new Date();
        Long id = reservationService.save(reservationDto.getBookId(), reservationDto.getUserId(), reservationDate);
        reservationDto.setBookId(id);
        reservationDto.setReservationDate(reservationDate);
        return new ResponseEntity<>(reservationDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update")
    public ResponseEntity update(@RequestBody Reservation reservationDto) {
        reservationService.update(reservationDto.getId());
        return new ResponseEntity<>(reservationDto, HttpStatus.OK);
    }
}
