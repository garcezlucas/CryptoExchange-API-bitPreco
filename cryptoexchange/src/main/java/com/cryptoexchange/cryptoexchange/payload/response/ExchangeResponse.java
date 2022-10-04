package com.cryptoexchange.cryptoexchange.payload.response;


public class ExchangeResponse {
    
    private Integer id;
    private String type;
    private Double value;
    private Double amount;
    private String date; 

    public ExchangeResponse() {

    }

    public ExchangeResponse(Integer Id, String type, Double amount, String date){
        this.type = type;
        this.amount = amount;
        this.date = date;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

}