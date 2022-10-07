package com.cryptoexchange.cryptoexchange.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


// Cria a tabela exchange e suas colunas no BD
@Entity
@Table(name = "exchange")
public class Exchange {
    //  Cria a coluna que armazena o valor do Id da transação com geração automática
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exchange_id")
    private Long id;
    //  Cria a coluna que armazena a criptomoeda da transação
    @Column(name = "exchange_market")
    private String market;
    //  Cria a coluna que armazena o tipo de transação
    @Column(name = "exchange_exchange")
    private String exchange;
    //  Cria a coluna que armazena o valor da transação com
    @Column(name = "exchange_value")
    private Double value;
    //  Cria a coluna que armazena a quantidade de criptomoedas negociadas
    @Column(name = "exchange_amount")
    private Double amount;
    //  Cria a coluna que armazena a data da transação
    @Column(name = "exchange_date")
    private String date; 

    public Exchange() {

    }

    public Exchange(Long id, String market, String exchange, Double value, Double amount, String date){
        this.market = market;
        this.exchange = exchange;
        this.value = value;
        this.amount = amount;
        this.date = date;

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

}
