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

    @Column(name = "authorName")
    @NotNull(message = "Should enter some name1")
    private String authorName;

    public Author() {
    }

    public Author(@NotNull(message = "Should eneter some name1") String authorName) {
        this.authorName = authorName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author that = (Author) o;

        return authorName.equals(that.authorName);
    }

    @Override
    public int hashCode() {
        return authorName.hashCode();
    }
}
