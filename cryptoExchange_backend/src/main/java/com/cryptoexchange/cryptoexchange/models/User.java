package com.cryptoexchange.cryptoexchange.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


//  Cria a coluna a tabela users
@Entity
@Table(	name = "users")
public class User {
    //  Cria a coluna que armazena o valor do Id do usuário com geração automatica
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INTEGER", unique = true)
    private Long id;
    //  Cria a coluna que armazena o nome do usuário
    @NotBlank
    @Size(min = 3, max = 25)
    @Column(name = "username", columnDefinition = "TEXT", nullable = false, unique = true, length = 25)
    private String username;
    //  Cria a coluna que armazena o email do usuário
    @NotBlank
    @Size(max = 80)
    @Email
    @Column(name = "email", columnDefinition = "TEXT", nullable = false, unique = true, length = 80)
    private String email;
    //  Cria a coluna que armazena o password do usuário 
    @NotBlank
    @Size(min = 6, max = 255)
    @Column(name = "password", columnDefinition = "TEXT", nullable = false, length = 255)
    private String password;
    // Define uma relação de muitos para muitos entre o id do usuário e o id da Role
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles", 
                    joinColumns = @JoinColumn(name = "users_id"),
                    inverseJoinColumns = @JoinColumn(name = "roles_id"))
    // Cria um Hash Code com a(s) Role atribuidas ao usuário
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

    public Long getUsername(Long id2) {
        return null;
    }
    
}

