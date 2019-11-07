package com.accenture.library.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name="authors")
public class Author {

    @Id
    @Column(name = "author_id")
    @SequenceGenerator(name = "seqAuthor", initialValue = 6, allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqAuthor")
    private Long id;

    @Column(name = "author_name")
    private String name;

    @Column(name = "deleted")
    private boolean deleted;

    public Author() {
    }

    public Author(Long id, String name, boolean deleted) {
        this.id=id;
        this.name = name;
        this.deleted = deleted;
    }

    public Author(@NotNull(message = "Should enter some name!") String name, boolean deleted) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        if (deleted != author.deleted) return false;
        if (!Objects.equals(id, author.id)) return false;
        return Objects.equals(name, author.name);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (deleted ? 1 : 0);
        return result;
    }
}
