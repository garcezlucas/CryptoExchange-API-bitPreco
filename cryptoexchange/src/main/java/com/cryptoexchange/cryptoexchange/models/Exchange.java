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
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exchange_id")
    private Integer id;

    @Column(name = "exchange_market")
    private String market;

    @Column(name = "exchange_exchange")
    private String exchange;

    @Column(name = "exchange_value")
    private Double value;

    @Column(name = "exchange_amount")
    private Double amount;

    @Column(name = "exchange_date")
    private String date; 

    public Exchange() {

    }

    public Exchange(Integer Id, String market, String exchange, Double value, Double amount, String date){
        this.market = market;
        this.exchange = exchange;
        this.value = value;
        this.amount = amount;
        this.date = date;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
