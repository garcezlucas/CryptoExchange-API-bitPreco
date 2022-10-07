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
    // Cria a coluna que armazena o id da criptomoeda com geração automatica
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coin_id")
    private Long id;
    // Cria a coluna qua armazena se a busca obtive sucesso ou não
    @Column(name = "coin_success")
    private String success;
    // Cria a coluna que armazena o nome da criptomoeda
    @Column(name = "coin_market")
    private String market;
    // Cria a coluna    ue armazena o ultimo valor de transação da criptomoeda
    @Column(name = "coin_last")
    private Double last;
    // Cria a coluna que armazena o valor mais alto de transação da criptomoeda
    @Column(name = "coin_high")
    private Double high;
    // Cria a coluna que armazena o valor mais baixo de transação  criptomoeda
    @Column(name = "coin_low")
    private Double low;  
    // Cria a coluna que armazena o valume de transação da criptomoeda no dia
    @Column(name = "coin_vol")
    private Double vol;
    // Cria a coluna que armazena o valor medio das transações da criptomoeda no dia
    @Column(name = "coin_avg")
    private Double avg;
    // Cria a coluna que armazena o valor da variação do preço da criptomoeda (diferença entre o valor mais alto e o mais baixo)
    @Column(name = "coin_var")
    private Double var;
    // Cria a coluna que armazena o valor de compra da criptomoeda
    @Column(name = "coin_buy")
    private Double buy;
    // Cria a coluna que armazena o valor de venda da criptomoeda
    @Column(name = "coin_sell")
    private Double sell;
    // Cria a coluna que armazena a informação do horário de obtenção dos dados
    @Column(name = "coin_timestamp")
    private String timestamp;


    public Coin() {

    }

    public Coin( Long id, String success, String market, Double last, Double high,Double low, Double vol, Double avg, Double var, Double buy, Double sell,String timestamp) {
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
