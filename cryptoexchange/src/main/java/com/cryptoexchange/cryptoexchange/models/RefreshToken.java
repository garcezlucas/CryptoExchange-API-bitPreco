package com.cryptoexchange.cryptoexchange.models;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


// Cria a tabela refreshtoken
@Entity(name = "refreshtoken")
public class RefreshToken {
    //  Cria a coluna que armazena o valor do Id do refreshtoken com geração automatica
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //  Realiza a realação entre o id do refreshtoken e o id do usuário
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    //  Cria a coluna que armazena o token
    @Column(nullable = false, unique = true)
    private String token;
    //  Cria a coluna que armazena a data de expiração do token
    @Column(nullable = false)
    private Instant expiryDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }
}