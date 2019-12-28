package com.accenture.library.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="authors")
public class Author {

    @Id
    @Column(name = "author_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author_name")
    private String name;

    @Column(name = "enabled")
    private boolean enabled;

    public Author() {
    }

    public Author(Long id, String name, boolean enabled) {
        this.id=id;
        this.name = name;
        this.enabled = enabled;
    }

    public Author(String name, boolean enabled) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        if (enabled != author.enabled) return false;
        if (!Objects.equals(id, author.id)) return false;
        return Objects.equals(name, author.name);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (enabled ? 1 : 0);
        return result;
    }
}
