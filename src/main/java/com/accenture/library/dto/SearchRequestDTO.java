package com.accenture.library.dto;

import javax.validation.constraints.NotEmpty;

public class SearchRequestDTO {


    private String title ;

    private String author ;

    private String genre ;


    public SearchRequestDTO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title.toLowerCase();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author.toLowerCase();
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
