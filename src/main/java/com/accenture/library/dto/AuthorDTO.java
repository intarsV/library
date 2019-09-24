package com.accenture.library.dto;

import javax.validation.constraints.NotEmpty;

public class AuthorDTO {
    @NotEmpty(message = "Name must be not blank")
    private Long id;
    private String name;
    private boolean deleted;

    public AuthorDTO() {
    }

    public AuthorDTO(@NotEmpty(message = "Name must be not blank") Long id, String name, boolean deleted) {
        this.id = id;
        this.name = name;
        this.deleted = deleted;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
