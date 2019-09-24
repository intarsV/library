package com.accenture.library.dto;

import java.util.Date;

public class ReservationDTO {

    private Long id;
    private Long bookId;
    private String bookTitle;
    private Long userId;
    private String userName;
    private Date reservationDate;
    private boolean handOut;
    private boolean returned;
    private boolean deleted;

    public ReservationDTO() {
    }

    public ReservationDTO(Long id, Long bookId, String bookTitle, Long userId, String userName, Date reservationDate, boolean handOut, boolean returned, boolean deleted) {
        this.id = id;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.userId = userId;
        this.userName = userName;
        this.reservationDate = reservationDate;
        this.handOut = handOut;
        this.returned = returned;
        this.deleted = deleted;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isHandOut() {
        return handOut;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setHandOut(boolean handOut) {
        this.handOut = handOut;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }
}
