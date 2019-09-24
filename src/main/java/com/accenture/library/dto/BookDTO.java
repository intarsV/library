package com.accenture.library.dto;

public class BookDTO {

    private Long id;
    private String title;
    private String authorName;
    private String genre;
    private int copies;
    private int available;
    private boolean deleted;

    public BookDTO() {
    }

    public BookDTO(Long id, String title, String authorName, String genre, int copies, int available, boolean deleted) {
        this.id = id;
        this.title = title;
        this.authorName = authorName;
        this.genre = genre;
        this.copies = copies;
        this.available = available;
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String  getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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

    public boolean isDeleted() {
        return deleted;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
