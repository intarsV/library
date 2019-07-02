package com.accenture.library.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="USER")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    @NotNull(message = "Should provide username!")
    private String name;

    @Column(name = "password")
    @NotNull(message = "Should provide password!")
    private String password;

    public User() {
    }

    public User(@NotNull(message = "Should provide username!") String name, @NotNull(message = "Should provide password!") String password) {
        this.name = name;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user1 = (User) o;

        if (id != null ? !id.equals(user1.id) : user1.id != null) return false;
        if (!name.equals(user1.name)) return false;
        return password.equals(user1.password);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }
}
