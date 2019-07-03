package com.accenture.library.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="AUTHOR")
public class Author {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "seqAuthor", initialValue = 4, allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqAuthor")
    private Long id;

    @Column(name = "name")
    @NotNull(message = "Should enter some name!")
    private String name;

    public Author() {
    }

    public Author(@NotNull(message = "Should enter some name!") String name) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author that = (Author) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
