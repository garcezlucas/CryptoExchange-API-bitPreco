package com.cryptoexchange.cryptoexchange.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cryptoexchange.cryptoexchange.repositories.PreRemove;


// Cria a tabela roles no BD
@Entity
@PreRemove
@Table(name = "roles")
public class Role {
    //  Cria a coluna que armazena o valor do Id da role com geração automatica
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //  Cria a coluna que armazena os nomes das Roles 
    @Enumerated(EnumType.STRING)
    @Column(length = 25)
    private ERole name;

    public Role(){
    }

    public Role(ERole name) {

        this.name = name;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }
}
