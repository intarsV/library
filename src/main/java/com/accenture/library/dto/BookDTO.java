package com.accenture.library.dto;

import com.accenture.library.customEnumValidation.EnumGenre;
import com.accenture.library.customEnumValidation.MyEnumValidation;

import javax.validation.constraints.Pattern;

public class BookDTO {

    private Long id;
    @Pattern(regexp = "^[a-zA-Zā-žĀ-ž0-9 ]*$")
    private String title;
    @Pattern(regexp = "^[a-zA-Zā-žĀ-ž0-9 ]*$")
    private String authorName;
    @MyEnumValidation(enumClass = EnumGenre.class)
    private String genre;
    private int copies;
    private int available;
    private boolean enabled;

    public BookDTO() {
    }

    public BookDTO(Long id, String title, String authorName, String genre, int copies, int available, boolean enabled) {
        this.id = id;
        this.title = title;
        this.authorName = authorName;
        this.genre = genre;
        this.copies = copies;
        this.available = available;
        this.enabled = enabled;
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

    public boolean isEnabled() {
        return enabled;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authorName='" + authorName + '\'' +
                ", genre='" + genre + '\'' +
                ", copies=" + copies +
                ", available=" + available +
                ", enabled=" + enabled +
                '}';
    }
}
