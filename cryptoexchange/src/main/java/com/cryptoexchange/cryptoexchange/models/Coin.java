package com.cryptoexchange.cryptoexchange.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


// Cria a tabela coin e suas colunas no BD
@Entity
@Table(name = "coin")
public class Coin {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coin_id")
    private Integer id;

    @Column(name = "coin_success")
    private String success;

    @Column(name = "coin_market")
    private String market;

    @Column(name = "coin_last")
    private Double last;

    @Column(name = "coin_high")
    private Double high;

    @Column(name = "coin_low")
    private Double low;  

    @Column(name = "coin_vol")
    private Double vol;

    @Column(name = "coin_avg")
    private Double avg;

    @Column(name = "coin_var")
    private Double var;

    @Column(name = "coin_buy")
    private Double buy;

    @Column(name = "coin_sell")
    private Double sell;

    @Column(name = "coin_timestamp")
    private String timestamp;


    public Coin() {

    }

    public Coin( Integer id, String success, String market, Double last, Double high,Double low, Double vol, Double avg, Double var, Double buy, Double sell,String timestamp) {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Coin findCoinById(Integer id2) {
        return null;
    }

}
