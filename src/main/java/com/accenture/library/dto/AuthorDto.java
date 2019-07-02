package com.accenture.library.dto;

import javax.validation.constraints.NotEmpty;

public class AuthorDto {
    @NotEmpty(message = "Name must be not blank")
    private Long id;
    private String name;

    public AuthorDto() {
    }

    public AuthorDto(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
