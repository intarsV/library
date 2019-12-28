package com.accenture.library.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @Column(name = "reservation_id")
//    @SequenceGenerator(name = "seqReservation", initialValue = 6, allocationSize = 100)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @Column(name = "reservationDate")
    private Date reservationDate;

    @Column(name = "status")
    private String status;

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

    public Reservation(Book book, User user, Date reservationDate, String status) {
        this.book = book;
        this.user = user;
        this.reservationDate = reservationDate;
        this.status = status;
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

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reservation that = (Reservation) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(book, that.book)) return false;
        if (!Objects.equals(user, that.user)) return false;
        if (!Objects.equals(reservationDate, that.reservationDate))
            return false;
        return Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (book != null ? book.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (reservationDate != null ? reservationDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
