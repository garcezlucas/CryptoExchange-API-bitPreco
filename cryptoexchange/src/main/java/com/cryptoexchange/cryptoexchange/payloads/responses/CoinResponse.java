package com.cryptoexchange.cryptoexchange.payloads.responses;


// // Dados que devem ser retornados pelo usuário
public class CoinResponse {
        
    private Long id;
    private String success;
    private String market;
    private Double last;
    private Double high;
    private Double low;  
    private Double vol;
    private Double avg;
    private Double var;
    private Double buy;
    private Double sell;
    private String timestamp;


    public CoinResponse() {
        
    }

    public CoinResponse( Long id, String success, String market, Double last, Double high,Double low, Double vol, Double avg, Double var, Double buy, Double sell,String timestamp) {
        this.id = id;
        this.success = success;
        this.market = market;
        this.last = last;
        this.high = high;
        this.low = low;
        this.vol = vol;
        this.avg = avg;
        this.var = var;
        this.buy = buy;
        this.sell = sell;
        this.timestamp = timestamp;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public Double getLast() {
        return last;
    }

    public void setLast(Double last) {
        this.last = last;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getVol() {
        return vol;
    }

    public void setVol(Double vol) {
        this.vol = vol;
    }

    public Double getAvg() {
        return avg;
    }

    public void setAvg(Double avg) {
        this.avg = avg;
    }

    public Double getVar() {
        return var;
    }

    public void setVar(Double var) {
        this.var = var;
    }

    public Double getBuy() {
        return buy;
    }

    public void setBuy(Double buy) {
        this.buy = buy;
    }

    public Double getSell() {
        return sell;
    }

    public void setSell(Double sell) {
        this.sell = sell;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}

