package com.cryptoexchange.cryptoexchange.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


// Cria a tabela exchange e suas colunas no BD
@Entity
@Table(name = "exchange")
public class Exchange {
    //  Cria a coluna que armazena o valor do Id da transação com geração automática
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exchange_id", columnDefinition = "INTEGER")
    private Long id;
    //  Cria a coluna que armazena a criptomoeda da transação
    @Column(name = "exchange_market", columnDefinition = "TEXT", nullable = false, length = 25 )
    private String market;
    //  Cria a coluna que armazena o tipo de transação
    @Column(name = "exchange_exchange", columnDefinition = "TEXT", nullable = false, length = 25)
    private String exchange;
    //  Cria a coluna que armazena o valor da transação com
    @Column(name = "exchange_value", columnDefinition = "INTEGER", nullable = false, length = 55)
    private Double value;
    //  Cria a coluna que armazena a quantidade de Reais negociadas
    @Column(name = "exchange_amount", columnDefinition = "INTEGER", nullable = false, length = 55)
    private Double amount;
    // //  Cria a coluna que armazena a quantidade de criptomoedas negociadas
    @Column(name = "exchange_price", columnDefinition = "DOUBLE PRECISION", nullable = false, length = 55)
    private Double price;
    //  Cria a coluna que armazena a data da transação
    @Column(name = "exchange_date", columnDefinition = "TEXT", nullable = false, length = 25)
    private String date; 

    @ManyToOne
    @JoinColumn(name = "u_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public Exchange() {

    }

    public Exchange(Long id, String market, String exchange, Double value, Double amount, Double price, String date, User user){
        this.market = market;
        this.exchange = exchange;
        this.value = value;
        this.amount = amount;
        this.price = price;
        this.date = date;
        this.user = user;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    } 

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public static List<Exchange> findByUser(long id2) {
        return null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
