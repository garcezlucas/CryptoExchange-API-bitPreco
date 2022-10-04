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

import com.cryptoexchange.cryptoexchange.model.Exchange;
import com.cryptoexchange.cryptoexchange.repository.ExchangeRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/api")
@Api(value = "API para controle de transações de criptomoedas")
public class ExchangeControllers {
    
    @Autowired
    private ExchangeRepository exchangeRepository;

    @ApiOperation(value = "Inseri uma nova transação no BD")
    @PostMapping("/exchanges/insert")
    public ResponseEntity<Exchange> insertExchange(@RequestBody Exchange exchange){
        
        //Salva a nova transação no BD
        exchangeRepository.save(exchange);

        // Retorna a nova transação do BD com status CREATED
        return new ResponseEntity<Exchange>(exchange,  HttpStatus.CREATED);
    }

    @ApiOperation(value = "Lista todas as transações salvas no BD")
    @GetMapping(value = "/exchanges/all")
    public ResponseEntity<List<Exchange>> listExchanges(){
        
        // Retorna uma lista com todas as transações salvas no BD
        return new ResponseEntity<List<Exchange>>(exchangeRepository.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Busca uma transação no BD atraves de seu Id")
    @GetMapping(value = "/exchanges/{exchange}")
    public ResponseEntity<List<Exchange>> listExchangesByExchange(@PathVariable("exchange") String exchange){

        // Retorna uma transação do BD através do Id
        return new ResponseEntity<List<Exchange>>(exchangeRepository.findByExchange(exchange), HttpStatus.OK);
    }

    @ApiOperation(value = "Atualiza uma transação salva no BD")
    @PutMapping("/exchanges/update/{id}")
    public ResponseEntity<Exchange> update (@PathVariable("id") Integer id, @RequestBody Exchange exchange){

        // Busca as informações da transação no BD através do Id
        Exchange _exchange = exchangeRepository.getExchangeById(id);

        // Realiza o update das informações da transação
        _exchange.setMarket(exchange.getMarket());
        _exchange.setExchange(exchange.getExchange());
        _exchange.setAmount(exchange.getAmount());
        _exchange.setDate(exchange.getDate());

        // Retorna a transação com as informações atualizadas
        return new ResponseEntity<>(exchangeRepository.save(_exchange), HttpStatus.OK);
        
    }

    @ApiOperation(value = "Deleta uma transação do BD")
    @DeleteMapping(value = "/exchanges/delete/{id}")
    public ResponseEntity<Exchange> delete (@PathVariable("id") Integer id) {

        // Deleta uma transação do BD
        exchangeRepository.deleteById(id);
        
        // Não retorna nenhuma informação para o cliete
        return ResponseEntity.noContent().build();
    }

    
    


}
