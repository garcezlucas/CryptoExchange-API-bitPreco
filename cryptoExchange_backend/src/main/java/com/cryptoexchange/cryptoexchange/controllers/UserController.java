package com.cryptoexchange.cryptoexchange.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cryptoexchange.cryptoexchange.models.User;
import com.cryptoexchange.cryptoexchange.payloads.responses.MessageResponse;
import com.cryptoexchange.cryptoexchange.repositories.RefreshTokenRepository;
import com.cryptoexchange.cryptoexchange.repositories.RoleRepository;
import com.cryptoexchange.cryptoexchange.repositories.UserRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "API de controle de usuário")
public class UserController{

    @Autowired
    UserRepository userRepository;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @ApiOperation(value = "Lista de todods os usuários")
    // @PreAuthorize("hasRole('ROLE_PREMIUM') or hasRole('ROLE_ADMIN')")
    //localhost:8080/api/auth/users/list/all - GET
    @GetMapping(value = "/users/list/all")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<List<User>> listUsers(){
        
        // Retorna uma lista com todas as moedas
        return new ResponseEntity<List<User>>(userRepository.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Buscando usuários por nome", produces = "application/json")
    // @PreAuthorize("hasRole('ROLE_PREMIUM') or hasRole('ROLE_ADMIN')")
    //localhost:8080/api/auth/users/?username=bob - GET
    @GetMapping("/users/fins/{username}")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<List<User>> findByName(@PathVariable("username") String username){

        // Busca o usuário no BD através do nome de usuário
        List<User> _users = userRepository.findByUsername(username);
        //Verifica se o usuário é nulo
        if(_users== null){
            // Retorna resposta de usuário não encontrado
            return new ResponseEntity<List<User>>(_users,HttpStatus.NOT_FOUND);
        }
        // Retorna uma lista com as informações do usuário pesquisado
        return new ResponseEntity<List<User>>(_users, HttpStatus.OK);
    }

    @ApiOperation(value = "Buscando um usuário por id", produces = "application/json")
    // @PreAuthorize("hasRole('ROLE_PREMIUM') or hasRole('ROLE_ADMIN')")
    //localhost:8080/api/auth/users/1 - GET
    @GetMapping("users/{id}")    
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<Optional<User>> getById(@PathVariable("id") Long id){

        // Busca o usuário no BD atravpes do Id
        Optional<User> _users = userRepository.findById(id);
        // Verifica se o usuário é nulo
        if (_users.isEmpty()) {
            // Retorna resposta de usuário não encontrado
            return new ResponseEntity<Optional<User>>(_users, HttpStatus.NOT_FOUND);   
        }
        // Retorna uma lista com as informações do usuário pesquisado
        return new ResponseEntity<Optional<User>>(_users, HttpStatus.OK);
    }

    @ApiOperation(value = "Atualizando o usuário")
    @PutMapping("users/update/{id}")
    // @PreAuthorize("hasRole('ROLE_PREMIUM') or hasRole('ROLE_ADMIN')")
    //localhost:8080/api/auth/users/update/{id} - PUT
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> updateById(@PathVariable("id") Long id, @Valid @RequestBody User user) {
        
        // Busca o usuário no BD atravpes do Id
        User _users = userRepository.getUserById(id);


        // if (userRepository.existsByUsername(user.getUsername()) && _users.getUsername() != user.getById()) {
        //     // Se o nome já existir retorna um erro de usuário existente
        //     return ResponseEntity
        //         .badRequest()
        //         .body(new MessageResponse("Error: Usuário já existe!"));
        // }
        // // Verifica o email no BD
        // if (userRepository.existsByEmail(user.getEmail()) && _users.getEmail() != user.getEmail()) {
        //     // Se o email já existir retorna um erro de email existente
        //     return ResponseEntity
        //         .badRequest()
        //         .body(new MessageResponse("Error: Email já está em uso!"));
        // }

        // Realiza o update das informações do usuário
        _users.setUsername(user.getUsername());
        _users.setEmail(user.getEmail());
        _users.setPassword(encoder.encode(user.getPassword()));

        userRepository.save(_users);

        return ResponseEntity.ok(new MessageResponse("Usuário atualizado com sucesso!"));
        
    };


    @ApiOperation(value = "Deletando o usuário")
    @DeleteMapping(value = "/users/delete/{id}")
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    //localhost:8080/api/auth/users/delete/{Id} - DELETE
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> delete (@PathVariable("id") Long id) {

        // Deleta uma moeda do BD através do ID
        userRepository.deleteById(id);
        // refreshTokenRepository.deleteByUser(id);
        //Verifica se o id é nulo
        if (id == null) {
            // Retorna resposta de usuário não encontrado
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }  
        // Retorna resposta de usuário deletado
        return ResponseEntity.ok(new MessageResponse("Usuário deletado com sucesso!"));
        
    }

}