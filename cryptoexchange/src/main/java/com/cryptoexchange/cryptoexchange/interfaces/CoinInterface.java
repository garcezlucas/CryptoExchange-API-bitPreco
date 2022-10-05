package com.cryptoexchange.cryptoexchange.interfaces;



import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cryptoexchange.cryptoexchange.payloads.responses.CoinResponse;

//Comunicação com a API do bitPreço
@FeignClient(url = "https://api.bitpreco.com/", name = "viacep")

public interface CoinInterface {

    //Obter moeda com base no código
    @GetMapping("{market}/ticker")
    CoinResponse getCoinByCoin(@PathVariable("market") String market);

}