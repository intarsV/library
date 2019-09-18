package com.accenture.library.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @Column(name = "book_id")
    @SequenceGenerator(name = "seqBook", initialValue = 5, allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqBook")
    private Long id;

    @Column(name = "book_title")
    @NotNull(message = "Should add some title!")
    private String title;

    @ManyToOne
    @JoinColumn(name = "book_author")
    @NotNull(message = "Should add some author!")
    private Author author;

    @Column(name = "book_genre")
    @NotNull(message = "Select genre!")
    private String genre;

    @Column(name = "copies")
    private int copies;

    @Column(name = "available")
    private int available;

    public Book() {
    }

    public Book(@NotNull(message = "Should add some title!") String title, @NotNull(message = "Should add some author!") Author author, @NotNull(message = "Select genre!") String genre, int copies, int available) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.copies = copies;
        this.available = available;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (!id.equals(book.id)) return false;
        if (!title.equals(book.title)) return false;
        if (!author.equals(book.author)) return false;
        return genre.equals(book.genre);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + genre.hashCode();
        return result;
    }
}
