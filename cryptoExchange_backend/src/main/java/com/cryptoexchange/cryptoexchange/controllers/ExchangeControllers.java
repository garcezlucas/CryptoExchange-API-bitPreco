package com.cryptoexchange.cryptoexchange.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
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
@Api(value = "API para controle de transações de criptomoedas")
public class ExchangeControllers {
    
    @Autowired
    private ExchangeRepository exchangeRepository;

    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "Inseri uma nova transação no BD")
    // @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_PREMIUM') or hasRole('ROLE_ADMIN')")
    //localhost:8080/api/auth/exchanges/insert - POST
    @PostMapping("/exchanges/{id}")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> createExchange(
        @PathVariable("id") long id,
        @RequestBody Exchange exchange
    ){
        User _user = userRepository.findById(id);

        //Atrelando um usuário a um contato
        exchange.setUser(_user);

        //Salvando um novo contato
        Exchange _exchange = exchangeRepository.save(exchange);

        return new ResponseEntity<>("Transação criada com sucesso", HttpStatus.CREATED);
    }

    @ApiOperation(value = "Lista todas as transações salvas no BD")
    // @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_PREMIUM') or hasRole('ROLE_ADMIN')")
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

    @GetMapping("/exchanges/users/{id}")
    //localhost:8080/api/users/1/contacts
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @ApiOperation(value = "Bucando lista de transações por id de usuário", produces = "application/json")
    public ResponseEntity<List<Exchange>> getByUser(@PathVariable("id") long id){

        List<Exchange> _exchanges = exchangeRepository.findByUser(id);

        if(_exchanges.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<Exchange>>(_exchanges, HttpStatus.OK);

    }

    @ApiOperation(value = "Busca transações no BD através do tipo de transação")
    // @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_PREMIUM') or hasRole('ROLE_ADMIN')")
    //localhost:8080/api/auth/exchanges/compra - GET
    @GetMapping(value = "/exchanges/{exchange}")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<List<Exchange>> listExchangesByExchange(@PathVariable("exchange") String exchange){
        // Verifica se o tipo de transação é nulo
        if (exchange == null) {
            //Retorna uma mensagem de não encontrado caso o tipo de transação seje nulo
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);   
        }else {
            // Retorna uma transação do BD através do Id
            return new ResponseEntity<List<Exchange>>(exchangeRepository.findByExchange(exchange), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Atualiza uma transação salva no BD")
    // @PreAuthorize("hasRole('ROLE_PREMIUM') or hasRole('ROLE_ADMIN')")
    //localhost:8080/api/auth/exchanges/update/1 - PUT
    @PutMapping("/exchanges/update/{id}")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<Exchange> update (@PathVariable("id") Long id, @RequestBody Exchange exchange){

        // Busca as informações da transação no BD através do Id
        Exchange _exchange = exchangeRepository.getExchangeById(id);
        // Verifica se o tipo de transação é nulo
        if (_exchange == null) {
            // Retorna uma mensgaem de não encontrado caso o tipo de transação seje nulo
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
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    //localhost:8080/api/auth/exchanges/delete/1 - DELETE
    @DeleteMapping(value = "/exchanges/delete/{id}")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> delete (@PathVariable("id") Long id) {

        // Deleta uma transação do BD
        exchangeRepository.deleteById(id);
        // Verifica se o id busacdo é nulo
        if (id == null) {
            // Retorna uma mensagem de não encontrada caso o id seje nulo
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }   

        // Retorna uma mensagem de transação deletada com sucesso
        return ResponseEntity.ok(new MessageResponse("Transação deletado com sucesso!"));
    }

    
    


}
