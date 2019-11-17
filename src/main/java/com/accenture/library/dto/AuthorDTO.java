package com.accenture.library.dto;

import javax.validation.constraints.Pattern;

public class AuthorDTO {

    private Long id;
    @Pattern(regexp = "^[a-zA-Zā-žĀ-ž0-9 ]*$")
    private String name;
    private boolean enabled;

    public AuthorDTO() {
    }

    public AuthorDTO( Long id, String name, boolean enabled) {
        this.id = id;
        this.name = name;
        this.enabled = enabled;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "AuthorDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
