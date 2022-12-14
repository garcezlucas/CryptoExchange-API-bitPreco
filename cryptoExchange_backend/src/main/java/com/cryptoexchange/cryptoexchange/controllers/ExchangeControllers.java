package com.cryptoexchange.cryptoexchange.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cryptoexchange.cryptoexchange.models.Exchange;
import com.cryptoexchange.cryptoexchange.models.User;
import com.cryptoexchange.cryptoexchange.payloads.responses.MessageResponse;
import com.cryptoexchange.cryptoexchange.repositories.ExchangeRepository;
import com.cryptoexchange.cryptoexchange.repositories.UserRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "API para controle de transações de criptomoedas")
public class ExchangeControllers {
    
    @Autowired
    private ExchangeRepository exchangeRepository;

    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "Inseri uma nova transação no BD")
    //localhost:8080/api/auth/exchanges/insert - POST
    @PostMapping("/exchanges/{id}")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 201, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> createExchange(
        @PathVariable("id") long id,
        @RequestBody Exchange exchange
    ){
        User _user = userRepository.findById(id);

        //Atrelando um usuário a transação
        exchange.setUser(_user);

        //Salvando uma nova transação
        exchangeRepository.save(exchange);

        return ResponseEntity.ok(new MessageResponse("Transação registrada com sucesso!"));
    }

    @ApiOperation(value = "Lista todas as transações salvas no BD")
    //localhost:8080/api/auth/exchanges/all - GET
    @GetMapping(value = "/exchanges/all")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 201, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<List<Exchange>> listExchanges(){
        
        // Retorna uma lista com todas as transações salvas no BD
        return ResponseEntity.ok().body(exchangeRepository.findAll());
    }

    @ApiOperation(value = "Busca uma transação através de seu Id")
    //localhost:8080/api/coins/list/1 - GET
    @GetMapping(value = "/exchanges/list/{id}")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 201, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity <?> listExchangeById(@PathVariable("id") Long id){

        // verifica se o id é nulo
        if (id == null) {
            // Retorna uma mensagem de transacao não encontrada se o id for nulo
            return ResponseEntity.badRequest().body(new MessageResponse("Transação não encontrada"));
        }else {
            // Retorna uma transação definida pelo Id
            return ResponseEntity.ok().body(exchangeRepository.findById(id));
        }
    }

    @ApiOperation(value = "Bucando lista de transações por id de usuário", produces = "application/json")
    @GetMapping("/exchanges/users/{id}")
    //localhost:8080/api/users/1/contacts
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 201, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })    
    public ResponseEntity<?> getByUser(@PathVariable("id") long id){
        // Busca a transação no BD através do Id
        List<Exchange> _exchanges = exchangeRepository.findByUser(id);
        // Verifica se a transação está vazia
        if(_exchanges.isEmpty()){
            return ResponseEntity.badRequest().body(new MessageResponse("Transação não encontrada"));
        }
        // Retorna uma lista com as transações pelo Id do usuário
        return ResponseEntity.ok().body(_exchanges);

    }

    @ApiOperation(value = "Busca transações no BD através do tipo de transação")
    //localhost:8080/api/auth/exchanges/compra - GET
    @GetMapping(value = "/exchanges/{exchange}")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 201, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> listExchangesByExchange(@PathVariable("exchange") String exchange){
        // Verifica se o tipo de transação é nulo
        if (exchange == null) {
            //Retorna uma mensagem de não encontrado caso o tipo de transação seje nulo
            return ResponseEntity.badRequest().body(new MessageResponse("Transação não encontrada")); 
        }else {
            // Retorna uma transação do BD através do Id
            return ResponseEntity.ok().body(exchangeRepository.findByExchange(exchange));
        }
    }

    @ApiOperation(value = "Atualiza uma transação salva no BD")
    //localhost:8080/api/auth/exchanges/update/1 - PUT
    @PutMapping("/exchanges/update/{id}")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 201, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> update (@PathVariable("id") Long id, @RequestBody Exchange exchange){

        // Busca as informações da transação no BD através do Id
        Exchange _exchange = exchangeRepository.getExchangeById(id);
        // Verifica se o tipo de transação é nulo
        if (_exchange == null) {
            // Retorna uma mensgaem de não encontrado caso o tipo de transação seje nulo
            return ResponseEntity.badRequest().body(new MessageResponse("Transação não encontrada"));   
        }else {
            // Realiza o update das informações da transação
            _exchange.setMarket(exchange.getMarket());
            _exchange.setExchange(exchange.getExchange());
            _exchange.setValue(exchange.getValue());
            _exchange.setAmount(exchange.getAmount());
            _exchange.setPrice(exchange.getPrice());
            _exchange.setDate(exchange.getDate());

            exchangeRepository.save(_exchange);

            // Retorna a transação com as informações atualizadas
            return ResponseEntity.ok(new MessageResponse("Transação atualizada com sucesso!"));
        }
    }

    @ApiOperation(value = "Deleta uma transação do BD")
    //localhost:8080/api/auth/exchanges/delete/1 - DELETE
    @DeleteMapping(value = "/exchanges/delete/{id}")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 201, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> delete (@PathVariable("id") Long id) {

        // Deleta uma transação do BD
        exchangeRepository.deleteById(id);
        // Verifica se o id busacdo é nulo
        if (id == null) {
            // Retorna uma mensagem de não encontrada caso o id seje nulo
            return ResponseEntity.badRequest().body(new MessageResponse("Transação não encontrada"));
        }   

        // Retorna uma mensagem de transação deletada com sucesso
        return ResponseEntity.ok(new MessageResponse("Transação deletada com sucesso!"));
    }

    @ApiOperation(value = "Deleta todas transações do BD")
    //localhost:8080/api/auth/exchanges/delete/1 - DELETE
    @DeleteMapping(value = "/exchanges/delete/all")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 201, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> delete () {

        // Deleta uma transação do BD
        exchangeRepository.deleteAll();
        // Retorna uma mensagem de transação deletada com sucesso
        return ResponseEntity.ok(new MessageResponse("Transações deletadas com sucesso!"));
    }

    
    


}


