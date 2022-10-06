
package com.cryptoexchange.cryptoexchange.controllers;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cryptoexchange.cryptoexchange.interfaces.CoinInterface;
import com.cryptoexchange.cryptoexchange.models.Coin;
import com.cryptoexchange.cryptoexchange.payloads.responses.CoinResponse;
import com.cryptoexchange.cryptoexchange.repositories.CoinRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping ("/api/auth")
@Api(value = "API controle de criptomoedas com cotação atualizada")
public class CoinController {

    @Autowired
    private CoinInterface coinInterface;

    @Autowired
    private CoinRepository coinRepository;

    @ApiOperation(value="Cria uma nova moeda")
    //localhost:8080/api/auth/btc-brl/ticket - GET
    @GetMapping("/{market}/ticker")
    public ResponseEntity<CoinResponse> getByMarket(@PathVariable String market) {

        //Buscando resultado da API do ViaCEP
        CoinResponse response = coinInterface.getCoinByCoin(market);

        // Cria uma nova moeda coin
        if(response.getMarket() != null){

            Coin coin = new Coin(
                response.getId(),
                response.getSuccess(),
                response.getMarket(),
                response.getLast(),
                response.getHigh(),
                response.getLow(),
                response.getVol(),
                response.getAvg(), 
                response.getVar(),
                response.getBuy(), 
                response.getSell(),
                response.getTimestamp()
            );

            // Salva a moeda no BD
            coinRepository.save(coin);
        }

        // Retorna a moeda no corpo da requisição ou constrói uma build nula
        return response.getMarket() != null ? ResponseEntity.ok().body(response)
        : ResponseEntity.notFound().build();
        
    }

    @ApiOperation(value = "Lista todas as moedas")
    //localhost:8080/api/auth/coins?list/all - GET
    @GetMapping(value = "/coins/list/all")
    public ResponseEntity<List<Coin>> listCoins(){
        
        // Retorna uma lista com todas as moedas
        return new ResponseEntity<List<Coin>>(coinRepository.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Busca uma moeda através de seu Id")
    //localhost:8080/api/coins/list/1 - GET
    @GetMapping(value = "/coins/list/{id}")
    public ResponseEntity <Optional<Coin>> listCoinsById(@PathVariable("id") Long id){

        // Retorna uma moeda definida pelo Id
        return new ResponseEntity<Optional<Coin>>(coinRepository.findById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Atualiza as informações de uma moeda")
    //localhost:8080/api/auth/coins/update/1 - PUT
    @PutMapping("/coins/update/{id}")
    public ResponseEntity<Coin> update (@PathVariable("id") Long id, @RequestBody Coin coin){

        // Busca as informações da moeda no BD
        Coin _coin = coinRepository.getCoinById(id);
        
        // Realiza o update das informações da moeda
        _coin.setSuccess(coin.getSuccess());
        _coin.setMarket(coin.getMarket());
        _coin.setLast(coin.getLast());
        _coin.setLow(coin.getLow());
        _coin.setVol(coin.getVol());
        _coin.setAvg(coin.getAvg());
        _coin.setVar(coin.getVar());
        _coin.setBuy(coin.getBuy());
        _coin.setSell(coin.getSell());
        _coin.setTimestamp(coin.getTimestamp());

        // Retorna as novas informações da moeda com status OK
        return new ResponseEntity<>(coinRepository.save(_coin), HttpStatus.OK);
    }

    @ApiOperation(value = "Deleta uma moeda através de seu Id")
    //localhost:8080/api/auth/coins/delete/1 - DELETE
    @DeleteMapping(value = "/coins/delete/{id}")
    public ResponseEntity<Coin> delete (@PathVariable("id") Long id) {

        // Deleta uma moeda do BD através do ID
        coinRepository.deleteById(id);
        
        // Retorna nenhuma informação ao cliente
        return ResponseEntity.noContent().build();
    }

}