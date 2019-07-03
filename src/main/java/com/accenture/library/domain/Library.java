package com.accenture.library.domain;

import javax.persistence.*;

@Entity
@Table(name="library")
public class Library {

    @Id
    Long id;

    @OneToOne
    @JoinColumn(name = "book")
    private Book book;
    private int copies;
    private int reserved;
    private int available;

    public Library() {
    }

    public Library(Book book, int copies, int reserved, int available) {
        this.book = book;
        this.copies = copies;
        this.reserved = reserved;
        this.available = available;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public int getReserved() {
        return reserved;
    }

    public void setReserved(int reserved) {
        this.reserved = reserved;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }
}
