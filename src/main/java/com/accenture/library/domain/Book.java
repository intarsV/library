package com.accenture.library.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @Column(name = "book_id")
    @SequenceGenerator(name = "seqBook", initialValue = 7, allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqBook")
    private Long id;

    @Column(name = "book_title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "book_author")
    private Author author;

    @Column(name = "book_genre")
    private String genre;

    @Column(name = "copies")
    private int copies;

    @Column(name = "available")
    private int available;

    @Column(name = "deleted", columnDefinition = "TINYINT", length = 1)
    private boolean deleted;

    public Book() {
    }

    public Book(String title, Author author, String genre, int copies, int available, boolean deleted) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.copies = copies;
        this.available = available;
        this.deleted = deleted;
    }

    public Book(Long bookId) {

        this.id = bookId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (copies != book.copies) return false;
        if (available != book.available) return false;
        if (deleted != book.deleted) return false;
        if (!Objects.equals(id, book.id)) return false;
        if (!Objects.equals(title, book.title)) return false;
        if (!Objects.equals(author, book.author)) return false;
        return Objects.equals(genre, book.genre);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + copies;
        result = 31 * result + available;
        result = 31 * result + (deleted ? 1 : 0);
        return result;
    }
}
