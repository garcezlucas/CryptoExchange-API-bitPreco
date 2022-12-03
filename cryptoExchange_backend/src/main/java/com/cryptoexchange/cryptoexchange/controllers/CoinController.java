
package com.cryptoexchange.cryptoexchange.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "API controle de criptomoedas com cotação atualizada")
public class CoinController {

    @Autowired
    private CoinInterface coinInterface;

    @Autowired
    private CoinRepository coinRepository;

    @ApiOperation(value="Cria uma nova criptomoeda")
    //localhost:8080/api/auth/btc-brl/ticket - GET
    @GetMapping("/{market}/ticker")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 201, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<CoinResponse> getByMarket(@PathVariable("market") String market) {

        //Buscando resultado da API do bitPreco
        CoinResponse response = coinInterface.getCoinByCoin(market);
        // Busca a criptomoeda no BD
        Coin _coin = coinRepository.getCoinByMarket(market);

        // Verifica se a busca da criptomoeda no BD retornou um valor igual de nulo
        if (_coin == null){
            // Cria uma nova criptomoeda coin
            Coin coin = new Coin(
                response.getId(),
                // response.getSuccess(),
                response.getMarket(),
                // response.getLast(),
                // response.getHigh(),
                // response.getLow(),
                // response.getVol(),
                // response.getAvg(), 
                // response.getVar(),
                response.getBuy(), 
                response.getSell(),
                response.getTimestamp()
            );

            // Salva a criptomoeda no BD
            coinRepository.save(coin);

        } else{
            // Realiza o update das informações da criptomoeda
            // _coin.setSuccess(response.getSuccess());
            _coin.setMarket(response.getMarket());
            // _coin.setLast(response.getLast());
            // _coin.setHigh(response.getHigh());
            // _coin.setLow(response.getLow());
            // _coin.setVol(response.getVol());
            // _coin.setAvg(response.getAvg());
            // _coin.setVar(response.getVar());
            _coin.setBuy(response.getBuy());
            _coin.setSell(response.getSell());
            _coin.setTimestamp(response.getTimestamp());

            // Salva as novas informações da criptomoeda
            coinRepository.save(_coin);
        }

        // Retorna a criptomoeda no corpo da requisição 
        return response.getMarket() != null ? ResponseEntity.ok().body(response)
        : ResponseEntity.notFound().build();
        
    }

    @ApiOperation(value = "Lista todas as criptomoeda")
    //localhost:8080/api/auth/coins/list/all - GET
    @GetMapping(value = "/coins/list/all")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 201, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<List<Coin>> listCoins(){
        
        // Retorna uma lista com todas as criptomoeda
        return ResponseEntity.ok().body(coinRepository.findAll());
    }

    @ApiOperation(value = "Buscando lista de criptomoeda por nome", produces = "application/json")
    //localhost:8080/api/auth/users/?username=bob - GET
    @GetMapping("/coins/find/{market}")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 201, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?>findByName(@PathVariable("market") String market){

        // Busca o usuário no BD através do nome da criptomoeda
        List<Coin> _coins = coinRepository.findCoinByMarket(market);
        //Verifica se a criptomoeda é nula
        if(_coins == null){
            // Retorna resposta da criptomoeda não encontrada
            return ResponseEntity.badRequest().body(new MessageResponse("Criptomoeda não encontrada"));
        }
        // Retorna uma lista com as informações da criptomoeda pesquisada
        return ResponseEntity.ok().body(_coins);
    }

    @ApiOperation(value = "Busca uma criptomoeda através de seu Id")
    //localhost:8080/api/coins/list/1 - GET
    @GetMapping(value = "/coins/list/{id}")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 201, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity <?> listCoinsById(@PathVariable("id") Long id){

        // verifica se o id é nulo
        if (id == null) {
            // Retorna uma mensagem de criptomoeda não encontrada se o id for nulo
            return ResponseEntity.badRequest().body(new MessageResponse("Criptomoeda não encontrada"));
        }else {
            // Retorna uma criptomoeda definida pelo Id
            return ResponseEntity.ok().body(coinRepository.findById(id));
        }
    }

    @ApiOperation(value = "Buscando criptomoeda por nome", produces = "application/json")
    //localhost:8080/api/auth/users/?username=bob - GET
    @GetMapping("/coins/{market}")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 201, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?>getByName(@PathVariable("market") String market){

        // Busca o usuário no BD através do nome da criptomoeda
        Coin _coins = coinRepository.getCoinByMarket(market);
        //Verifica se a criptomoeda é nula
        if(_coins == null){
            // Retorna resposta da criptomoeda não encontrado
            return ResponseEntity.badRequest().body(new MessageResponse("Criptomoeda não encontrada"));
        }
        // Retorna uma lista com as informações da criptomoeda pesquisada
        return ResponseEntity.ok().body(_coins);
    }

    @ApiOperation(value = "Atualiza as informações de uma criptomoeda manualmente através de seu Id")
    //localhost:8080/api/auth/coins/update/1 - PUT
    @PutMapping("/coins/update/{id}")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 201, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> update (@PathVariable("id") Long id, @RequestBody Coin coin){

        // Busca as informações da criptomoeda no BD
        Coin _coin = coinRepository.getCoinById(id);
        
        // Realiza o update das informações da criptomoeda
        // _coin.setSuccess(coin.getSuccess());
        _coin.setMarket(coin.getMarket());
        // _coin.setLast(coin.getLast());
        // _coin.setHigh(coin.getHigh());
        // _coin.setLow(coin.getLow());
        // _coin.setVol(coin.getVol());
        // _coin.setAvg(coin.getAvg());
        // _coin.setVar(coin.getVar());
        _coin.setBuy(coin.getBuy());
        _coin.setSell(coin.getSell());
        _coin.setTimestamp(coin.getTimestamp());

        coinRepository.save(_coin);

        // Retorna as novas informações da criptomoeda com status OK
        return ResponseEntity.ok(new MessageResponse("Criptomoeda atualizada com sucesso!"));
    }

    @ApiOperation(value = "Deleta uma criptomoeda através de seu Id")
    //localhost:8080/api/auth/coins/delete/1 - DELETE
    @DeleteMapping(value = "/coins/delete/{id}")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 201, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> delete (@PathVariable("id") Long id) {

        // Deleta uma moeda do BD através do ID
        coinRepository.deleteById(id);
        // Verifica se o id é igual a nulo
        if (id == null) {
            //Retorna uma resposta de não encontrado caso o id seje nulo
            return ResponseEntity.badRequest().body(new MessageResponse("Criptomoeda não encontrada"));
        }
            // Retorna uma mensagem de criptomoeda deletada com sucesso
            return ResponseEntity.ok(new MessageResponse("Criptomoeda deletada com sucesso!"));
    }

    @ApiOperation(value = "Deleta todas criptomoedas do BD")
    //localhost:8080/api/auth/coins/delete/1 - DELETE
    @DeleteMapping(value = "/coins/delete/all")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 201, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> delete () {

        // Deleta uma transação do BD
        coinRepository.deleteAll();
        // Retorna uma mensagem de criptomoeda deletada com sucesso
        return ResponseEntity.ok(new MessageResponse("Criptomoedas deletadas com sucesso!"));
    }


}