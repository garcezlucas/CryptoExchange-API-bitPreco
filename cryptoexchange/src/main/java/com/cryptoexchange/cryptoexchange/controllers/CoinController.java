
package com.cryptoexchange.cryptoexchange.controllers;


import java.util.List;

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

import com.cryptoexchange.cryptoexchange.model.Coin;
import com.cryptoexchange.cryptoexchange.interfaces.CoinInterface;
import com.cryptoexchange.cryptoexchange.repository.CoinRepository;
import com.cryptoexchange.cryptoexchange.payload.response.CoinResponse;

@RestController
@RequestMapping ("/api")
public class CoinController {

    @Autowired
    private CoinInterface coinInterface;

    @Autowired
    private CoinRepository coinRepository;

    @GetMapping("/{market}/ticker")
    public ResponseEntity<CoinResponse> getByMarket(@PathVariable String market) {

        //Buscando resultado da API do ViaCEP
        CoinResponse response = coinInterface.getCoinByCoin(market);

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

            coinRepository.save(coin);
        }

        return response.getMarket() != null ? ResponseEntity.ok().body(response)
        : ResponseEntity.notFound().build();
        
    }

    @GetMapping(value = "/coins/list/all")
    public ResponseEntity<List<Coin>> listCoins(){
        
        return new ResponseEntity<List<Coin>>(coinRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/coins/list/{id}")
    public ResponseEntity <List<Coin>> listCoinsById(@PathVariable("id") Integer id){

        return new ResponseEntity<List<Coin>>(coinRepository.findById(id), HttpStatus.OK);
    }


    @PutMapping("/coins/update/{id}")
    public ResponseEntity<Coin> update (@PathVariable("id") Integer id, @RequestBody Coin coin){

        Coin _coin = coinRepository.getCoinById(id);

        
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


        System.out.println("_coin");

        return new ResponseEntity<>(coinRepository.save(_coin), HttpStatus.OK);

    }


    @DeleteMapping(value = "/coins/delete/{id}")
    public ResponseEntity<Coin> delete(@PathVariable("id") Integer id) {

        coinRepository.deleteById(id);
        
        return ResponseEntity.noContent().build();
    }


    

    
}