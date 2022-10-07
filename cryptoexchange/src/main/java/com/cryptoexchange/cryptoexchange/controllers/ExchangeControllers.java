package com.cryptoexchange.cryptoexchange.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cryptoexchange.cryptoexchange.models.Exchange;
import com.cryptoexchange.cryptoexchange.payloads.responses.MessageResponse;
import com.cryptoexchange.cryptoexchange.repositories.ExchangeRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping("/api/auth")
@Api(value = "API para controle de transações de criptomoedas")
public class ExchangeControllers {
    
    @Autowired
    private ExchangeRepository exchangeRepository;

    @ApiOperation(value = "Inseri uma nova transação no BD")
    //localhost:8080/api/auth/exchanges/insert - POST
    @PostMapping("/exchanges/insert")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<Exchange> insertExchange(@RequestBody Exchange exchange){
        
        //Salva a nova transação no BD
        exchangeRepository.save(exchange);

        // Retorna a nova transação do BD com status CREATED
        return new ResponseEntity<Exchange>(exchange,  HttpStatus.CREATED);
    }

    @ApiOperation(value = "Lista todas as transações salvas no BD")
    //localhost:8080/api/auth/exchanges/all - GET
    @GetMapping(value = "/exchanges/all")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<List<Exchange>> listExchanges(){
        
        // Retorna uma lista com todas as transações salvas no BD
        return new ResponseEntity<List<Exchange>>(exchangeRepository.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Busca transações no BD através do tipo de transação")
    //localhost:8080/api/auth/exchanges/compra - GET
    @GetMapping(value = "/exchanges/{exchange}")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<List<Exchange>> listExchangesByExchange(@PathVariable("exchange") String exchange){

        if (exchange == null) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);   
        }else {
            // Retorna uma transação do BD através do Id
            return new ResponseEntity<List<Exchange>>(exchangeRepository.findByExchange(exchange), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Atualiza uma transação salva no BD")
    //localhost:8080/api/auth/exchanges/update/1 - PUT
    @PutMapping("/exchanges/update/{id}")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<Exchange> update (@PathVariable("id") Long id, @RequestBody Exchange exchange){

        // Busca as informações da transação no BD através do Id
        Exchange _exchange = exchangeRepository.getExchangeById(id);
        if (_exchange == null) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);   
        }else {
            // Realiza o update das informações da transação
            _exchange.setMarket(exchange.getMarket());
            _exchange.setExchange(exchange.getExchange());
            _exchange.setValue(exchange.getValue());
            _exchange.setAmount(exchange.getAmount());
            _exchange.setDate(exchange.getDate());

            // Retorna a transação com as informações atualizadas
            return new ResponseEntity<>(exchangeRepository.save(_exchange), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Deleta uma transação do BD")
    //localhost:8080/api/auth/exchanges/delete/1 - DELETE
    @DeleteMapping(value = "/exchanges/delete/{id}")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> delete (@PathVariable("id") Long id) {

        // Deleta uma transação do BD
        exchangeRepository.deleteById(id);
        if (id == null) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }   

        // Retorna nenhuma informação ao cliente
        return ResponseEntity.ok(new MessageResponse("Transação deletado com sucesso!"));
    }

    
    


}
