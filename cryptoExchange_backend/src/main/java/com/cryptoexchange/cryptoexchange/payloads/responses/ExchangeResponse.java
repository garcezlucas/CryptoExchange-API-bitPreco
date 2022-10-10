package com.cryptoexchange.cryptoexchange.payloads.responses;


// Dados que devem ser retornados pelo us
public class ExchangeResponse {
    
    private Long id;
    private String market;
    private String exchange;
    private Double value;
    private Double amount;
    private String date; 

    public ExchangeResponse() {
        
    }

    public ExchangeResponse(Long id, String market, String exchange, Double value, Double amount, String date){
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
