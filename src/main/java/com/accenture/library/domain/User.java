package com.accenture.library.domain;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    @SequenceGenerator(name = "seqUserName", initialValue = 4, allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqUserName")
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_authorities", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<Authority> authorities;

    public User() {
    }

    public User( String userName, String password, boolean enabled, Set<Authority> authorities) {
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    public User(String userName, String password, Set<Authority> authorities) {
        this.userName = userName;
        this.password = password;
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (enabled != user.enabled) return false;
        if (!Objects.equals(id, user.id)) return false;
        if (!Objects.equals(userName, user.userName)) return false;
        if (!Objects.equals(password, user.password)) return false;
        return Objects.equals(authorities, user.authorities);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (enabled ? 1 : 0);
        result = 31 * result + (authorities != null ? authorities.hashCode() : 0);
        return result;
    }
}
