package com.cryptoexchange.cryptoexchange.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Size(min = 3, max = 25)
    @Column(name = "username", columnDefinition = "TEXT", nullable = false, unique = true, length = 20)
    private String username;

    @NotBlank
    @Size(max = 80)
    @Email
    @Column(name = "email", columnDefinition = "TEXT", nullable = false, unique = true, length = 255)
    private String email;

    @NotBlank
    //@Size(min = 8, max = 20)
    //@Pattern(regexp = "^[a-zA-Z0-9_.-]*$")
    @Column(name = "password", columnDefinition = "TEXT", nullable = false, length = 255)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles", 
                joinColumns = @JoinColumn(name = "users_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    
    
}

