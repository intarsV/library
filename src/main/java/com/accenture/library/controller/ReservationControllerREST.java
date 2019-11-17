package com.accenture.library.controller;

import com.accenture.library.dto.ReservationDTO;
import com.accenture.library.service.reservation.ReservationService;
import com.accenture.library.service.reservation.ReservationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
@CrossOrigin(origins = "http://localhost:3000")  //should remove on production
public class ReservationControllerREST {

    private static final String ADMIN_AUTHORITY = "ADMIN";

    private ReservationService reservationService;

    @Autowired
    public ReservationControllerREST(ReservationServiceImpl bookReservationSvr) {
        this.reservationService = bookReservationSvr;
    }

    @GetMapping
    public List<ReservationDTO> searchByParameters(final ReservationDTO reservationDTO,
                                                   Authentication authentication) {
        final String bookTitle = reservationDTO.getBookTitle();
        final String status = reservationDTO.getStatus();
        final boolean isAdmin = checkIsAdmin(authentication.getAuthorities());
        final String user;
        if (!isAdmin) {
            user = authentication.getName();
        } else {
            user = reservationDTO.getUserName();
        }
        return reservationService.getByParameters(bookTitle, user, status, isAdmin);
    }

    @PostMapping
    public ResponseEntity saveReservation(@RequestBody ReservationDTO reservationDto, Authentication authentication) {
        final String userName = authentication.getName();
        return new ResponseEntity<>(reservationService.makeReservation(reservationDto.getBookId(),
                userName), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity updateReservation(@PathVariable Long id,
                                            @RequestBody ReservationDTO reservationDto,
                                            Authentication authentication) {
        final Long reservationId = id;
        final String userName = authentication.getName();
        final String status = reservationDto.getStatus();
        final boolean isAdmin = checkIsAdmin(authentication.getAuthorities());
        return new ResponseEntity<>(reservationService.updateStatus(reservationId, userName, status, isAdmin), HttpStatus.ACCEPTED);
    }

    //AUXILIARY methods

    private static boolean checkIsAdmin(Collection<? extends GrantedAuthority> authorities) {
        for (GrantedAuthority grantedAuthority : authorities) {
            if (ADMIN_AUTHORITY.equals(grantedAuthority.getAuthority())) {
                return true;
            }
        }
        return false;
    }
}
