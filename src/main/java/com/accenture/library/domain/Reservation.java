package com.accenture.library.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @Column(name = "reservation_id")
    @SequenceGenerator(name = "seqReservation", initialValue = 6, allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqReservation")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @Column(name = "reservationDate")
    private Date reservationDate;

    @Column(name = "handOut", columnDefinition = "TINYINT", length = 1)
    private boolean handOut;

    @Column(name = "returned", columnDefinition = "TINYINT", length = 1)
    private boolean returned;

    @Column(name = "deleted", columnDefinition = "TINYINT", length = 1)
    private boolean deleted;

    public Reservation() {
    }

    public Reservation(Long id) {
        this.id = id;
    }

    public Reservation(Book book, User user, Date reservationDate) {
        this.book = book;
        this.user = user;
        this.reservationDate = reservationDate;
    }

    public Reservation(Book book, User user, Date reservationDate, boolean handOut, boolean returned, boolean deleted) {
        this.book = book;
        this.user = user;
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

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public boolean isHandOut() {
        return handOut;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
