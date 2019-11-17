package com.accenture.library.dto;

import com.accenture.library.domain.EnumReservationStatus;

import java.util.Date;

public class ReservationDTO {

    private Long id;
    private Long bookId;
    private String bookTitle;
    private Long userId;
    private String userName;
    private Date reservationDate;
    private String status;

    public ReservationDTO() {
    }

    public ReservationDTO(Long id, Long bookId, String bookTitle, Long userId, String userName, Date reservationDate, String status) {
        this.id = id;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.userId = userId;
        this.userName = userName;
        this.reservationDate = reservationDate;
        this.status = status;
    }

    public ReservationDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
