package com.accenture.library.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "authorities")
public class Authority {

    @Id
    @Column(name = "authority_id")
//    @SequenceGenerator(name = "seqAuthority ", initialValue = 3, allocationSize = 100)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqAuthority ")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "authority_name")
    private String authorityName;

    public Authority() {
    }

    public Authority(Long id, String authorityName) {
        this.id = id;
        this.authorityName = authorityName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Authority authorities1 = (Authority) o;

        if (!Objects.equals(id, authorities1.id)) return false;
        return Objects.equals(authorityName, authorities1.authorityName);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (authorityName != null ? authorityName.hashCode() : 0);
        return result;
    }
}
