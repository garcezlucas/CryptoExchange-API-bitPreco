
package com.cryptoexchange.cryptoexchange.controllers;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.cryptoexchange.cryptoexchange.payloads.responses.MessageResponse;
import com.cryptoexchange.cryptoexchange.repositories.CoinRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping ("/api/auth")
@Api(value = "API controle de criptomoedas com cotação atualizada")
public class CoinController {

    @Autowired
    private CoinInterface coinInterface;

    @Autowired
    private CoinRepository coinRepository;

    @ApiOperation(value="Cria uma nova moeda")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_PREMIUM') or hasRole('ROLE_ADMIN')")
    //localhost:8080/api/auth/btc-brl/ticket - GET
    @GetMapping("/{market}/ticker")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<CoinResponse> getByMarket(@PathVariable("market") String market) {

        //Buscando resultado da API do bitPreco
        CoinResponse response = coinInterface.getCoinByCoin(market);
        // Busca a moeda no BD
        Coin _coin = coinRepository.getCoinByMarket(market);

        // Verifica se a busca da moeda no BD retornou um valor igual de nulo
        if (_coin == null){
            // Cria uma nova moeda coin
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

        } else{
            // Realiza o update das informações da moeda
            _coin.setSuccess(response.getSuccess());
            _coin.setMarket(response.getMarket());
            _coin.setLast(response.getLast());
            _coin.setHigh(response.getHigh());
            _coin.setLow(response.getLow());
            _coin.setVol(response.getVol());
            _coin.setAvg(response.getAvg());
            _coin.setVar(response.getVar());
            _coin.setBuy(response.getBuy());
            _coin.setSell(response.getSell());
            _coin.setTimestamp(response.getTimestamp());

            // Salva as novas informações da moeda
            coinRepository.save(_coin);
        }

        // Retorna a moeda no corpo da requisição 
        return response.getMarket() != null ? ResponseEntity.ok().body(response)
        : ResponseEntity.notFound().build();
        
    }

    @ApiOperation(value = "Lista todas as moedas")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_PREMIUM') or hasRole('ROLE_ADMIN')")
    //localhost:8080/api/auth/coins?list/all - GET
    @GetMapping(value = "/coins/list/all")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<List<Coin>> listCoins(){
        
        // Retorna uma lista com todas as moedas
        return new ResponseEntity<List<Coin>>(coinRepository.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Busca uma moeda através de seu Id")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_PREMIUM') or hasRole('ROLE_ADMIN')")
    //localhost:8080/api/coins/list/1 - GET
    @GetMapping(value = "/coins/list/{id}")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity <Optional<Coin>> listCoinsById(@PathVariable("id") Long id){

        // verifica se o id é nulo
        if (id == null) {
            // Retorna uma mensagem de moeda não encontrada se o id for nulo
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);   
        }else {
            // Retorna uma moeda definida pelo Id
            return new ResponseEntity<Optional<Coin>>(coinRepository.findById(id), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Atualiza as informações de uma moeda manualmente através de seu Id")
    @PreAuthorize("hasRole('ROLE_PREMIUM') or hasRole('ROLE_ADMIN')")
    //localhost:8080/api/auth/coins/update/1 - PUT
    @PutMapping("/coins/update/{id}")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<Coin> update (@PathVariable("id") Long id, @RequestBody Coin coin){

        // Busca as informações da moeda no BD
        Coin _coin = coinRepository.getCoinById(id);
        
        // Realiza o update das informações da moeda
        _coin.setSuccess(coin.getSuccess());
        _coin.setMarket(coin.getMarket());
        _coin.setLast(coin.getLast());
        _coin.setHigh(coin.getHigh());
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    //localhost:8080/api/auth/coins/delete/1 - DELETE
    @DeleteMapping(value = "/coins/delete/{id}")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> delete (@PathVariable("id") Long id) {

        // Deleta uma moeda do BD através do ID
        coinRepository.deleteById(id);
        // Verifica se o id é igual a nulo
        if (id == null) {
            //Retorna uma resposta de não encontrado caso o id seje nulo
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);   
        }
            // Retorna uma mensagem de moeda deletada com sucesso
            return ResponseEntity.ok(new MessageResponse("Coin deletada com sucesso!"));
    }

}