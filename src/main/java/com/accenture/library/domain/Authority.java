package com.accenture.library.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "authority")
public class Authority {

    @Id
    @Column(name = "authority_id")
    @SequenceGenerator(name = "seqAuthority ", initialValue = 3, allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqAuthority ")
    private Long id;

    @Column(name = "authority")
    private String authority;

    public Authority() {
    }

    public Authority(Long id, String authority) {
        this.id = id;
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Authority authorities1 = (Authority) o;

        if (!Objects.equals(id, authorities1.id)) return false;
        return Objects.equals(authority, authorities1.authority);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (authority != null ? authority.hashCode() : 0);
        return result;
    }
}
