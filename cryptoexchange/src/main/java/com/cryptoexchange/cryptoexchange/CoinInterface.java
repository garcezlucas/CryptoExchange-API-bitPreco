package com.cryptoexchange.cryptoexchange;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//Comunicação com a API do ViaCEP
@FeignClient(url = "https://api.bitpreco.com/", name = "viacep")

public interface CoinInterface {

    //Obter moeda com base no código
    @GetMapping("{coin}/ticker")
    Coin getCoinByCoin(@PathVariable("coin") String coin);

}