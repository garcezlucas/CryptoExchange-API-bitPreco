package com.cryptoexchange.cryptoexchange.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cryptoexchange.cryptoexchange.exceptions.TokenRefreshException;
import com.cryptoexchange.cryptoexchange.models.ERole;
import com.cryptoexchange.cryptoexchange.models.RefreshToken;
import com.cryptoexchange.cryptoexchange.models.Role;
import com.cryptoexchange.cryptoexchange.models.User;
import com.cryptoexchange.cryptoexchange.payloads.requests.LoginRequest;
import com.cryptoexchange.cryptoexchange.payloads.requests.SignupRequest;
import com.cryptoexchange.cryptoexchange.payloads.requests.TokenRefreshRequest;
import com.cryptoexchange.cryptoexchange.payloads.responses.MessageResponse;
import com.cryptoexchange.cryptoexchange.payloads.responses.TokenRefreshResponse;
import com.cryptoexchange.cryptoexchange.payloads.responses.UserInfoResponse;
import com.cryptoexchange.cryptoexchange.repositories.RoleRepository;
import com.cryptoexchange.cryptoexchange.repositories.UserRepository;
import com.cryptoexchange.cryptoexchange.securities.jwt.JwtUtils;
import com.cryptoexchange.cryptoexchange.securities.services.RefreshTokenService;
import com.cryptoexchange.cryptoexchange.securities.services.UserDetailsImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "API de autenticação de usuário")
public class AuthController{
    
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    @ApiOperation(value = "Login do usuário")
    //localhost:8080/api/auth/signin - POST
    @PostMapping("/signin")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        // Chama o AuthenticationManager do Spring Framework Authentication
        Authentication authentication = authenticationManager.authenticate(
            // Pega o nome de usuário e a senha usados no pedido de login para autenticação
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        // Realiza a autenticação do usuário
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Pega as informações do usuário autenticado
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal(); 
        // Chama a função generateJwtToken para gerar um cookie Jwt
        String jwtCookie = jwtUtils.generateJwtToken(userDetails);
        // Cria uma lista com a(s) Role atribuidas ao usuário
        List<String> roles = userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());
        // Chama a função createRefreshToken para gerar um novo refreshToken
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        //Retorna uma resposta com os dados do usuário
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                        .body(new UserInfoResponse(jwtCookie,
                            refreshToken.getToken(),
                            userDetails.getId(), 
                            userDetails.getUsername(), 
                            userDetails.getEmail(), 
                            roles));
    }


    @ApiOperation(value = "Criando um novo usuário", consumes = "application/json", produces = "application/json")
    //localhost:8080/api/auth/signup - POST
    @PostMapping("/signup")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 201, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        // Verifica o nome de usuário no BD
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            // Se o nome já existir retorna um erro de usuário existente
            return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: Usuário já existe!"));
            }
            // Verifica o email no BD
            if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            // Se o email já existir retorna um erro de email existente
            return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: Email já está em uso!"));
            }

            // Cria um novo usuário com requisição de nome de usuário, email, senha e permissão
            User user = new User(signUpRequest.getUsername(), 
                    signUpRequest.getEmail(),
                    encoder.encode(signUpRequest.getPassword()));
            // Pega a Role inserida pelo usuário
            Set<String> strRoles = signUpRequest.getRole();
            // 
            Set<Role> roles = new HashSet<>();
            // Verifica se o campo Role do input é nulo
            if (strRoles == null) {
            //Se o input for nulo é atribuido a ROLE_USER para o usuário
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                // Lança excessão informando que a Role não foi encontrada
                .orElseThrow(() -> new RuntimeException("Error: Role não encontrada."));
            roles.add(userRole);
            } else {
            strRoles.forEach(role -> {
                switch (role) {
                    //Se o input for admin é atribuido a ROLE_ADMIN para o usuário
                    case "admin":
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                        // Lança excessão informando que a Role não foi encontrada
                        .orElseThrow(() -> new RuntimeException("Error: Role não encontrada."));
                    roles.add(adminRole);

                    //Se o input for premium é atribuido a ROLE_PREMIUM para o usuário
                    break;
                    case "premium":
                    Role modRole = roleRepository.findByName(ERole.ROLE_PREMIUM)
                        // Lança excessão informando que a Role não foi encontrada
                        .orElseThrow(() -> new RuntimeException("Error: Role não encontrada."));
                    roles.add(modRole);

                    // Por default é atribuido a ROLE_USER para o usuário
                    break;
                    default:
                    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                        // Lança excessão informando que a Role não foi encontrada
                        .orElseThrow(() -> new RuntimeException("Error: Role não encontrada."));
                    roles.add(userRole);
                    }
            });
            }
            // Adiciona uma Role ao usuário
            user.setRoles(roles);
            // Salva os dados de user no BD
            userRepository.save(user);
            // Retorna uma mensagem de usuário registrado
            return ResponseEntity.ok(new MessageResponse("Usuário registrado com sucesso!"));
        }

    @PostMapping("/refreshtoken")
    //localhost:8080/api/auth/refreshtoken - POST
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {

        // Chama a função getRefreshToken para pegar o RefreshToken
        String requestRefreshToken = request.getRefreshToken();
        // Retorna uma resposta com as informações do Refresh Token
        return refreshTokenService.findByToken(requestRefreshToken)
            .map(refreshTokenService::verifyExpiration)
            .map(RefreshToken::getUser)
            .map(user -> {
                // Chama a função generateTokenFromUsername para gerar um novo token atraves do nome de usuário
                String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                // Retorna as informações do Token gerado
                return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
            })
            // Retorna uma mensagem de Erro se o token não for encontrado no BD
            .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                "Refresh token não está no Banco de Dados!"));
        }    


    @ApiOperation(value = "Logout do usuário")
    //localhost:8080/api/auth/signout - POST
    @PostMapping("/signout")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> logoutUser() {

        // Chama a funçaõ getCleanJwtCookie para limpar o cookie
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        //Retorna uma mensagem de Sessão Encerrada
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("Sessão encerrada"));
    }

    @ApiOperation(value = "Buscando usuários por nome", produces = "application/json")
    //localhost:8080/api/auth/users/?username=bob - GET
    @GetMapping("/users/")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<Optional<User>> findByName(@RequestParam String username){

        // Busca o usuário no BD através do nome de usuário
        Optional<User> _users = userRepository.findByUsername(username);
        //Verifica se o usuário é nulo
        if(_users.isEmpty()){
            // Retorna resposta de usuário não encontrado
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // Retorna uma lista com as informações do usuário pesquisado
        return new ResponseEntity<Optional<User>>(_users, HttpStatus.OK);
    }

    @ApiOperation(value = "Buscando um usuário por id", produces = "application/json")
    //localhost:8080/api/auth/users/1 - GET
    @GetMapping("users/{id}")    
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<Optional<User>> getById(@PathVariable("id") Long id){

        // Busca o usuário no BD atravpes do Id
        Optional<User> _user = userRepository.findById(id);
        // Verifica se o usuário é nulo
        if (_user == null) {
            // Retorna resposta de usuário não encontrado
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);   
        }
        // Retorna uma lista com as informações do usuário pesquisado
        return new ResponseEntity<Optional<User>>(_user, HttpStatus.OK);
    }

    @PutMapping("users/update/{id}")
    //localhost:8080/api/auth/users/update/{id} - PUT
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<User> updateById(@PathVariable("id") Long id, @RequestBody User user) {
        
        // Busca o usuário no BD atravpes do Id
        User _user = userRepository.getUserById(id);
        // Verifica se o usuário é nulo
        if (_user == null) {
            // Retorna resposta de usuário não encontrado
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);   
        }else {
            // Realiza o update das informações da moeda
            _user.setUsername(user.getUsername());
            _user.setEmail(user.getEmail());
            _user.setPassword(user.getPassword());

            // Retorna as novas informações da moeda com status OK
            return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/users/delete/{id}")
    //localhost:8080/api/auth/users/delete/{Id} - DELETE
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> delete (@PathVariable("id") Long id) {

        // Deleta uma moeda do BD através do ID
        userRepository.deleteById(id);
        //Verifica se o id é nulo
        if (id == null) {
            // Retorna resposta de usuário não encontrado
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }  
        // Retorna resposta de usuário deletado
        return ResponseEntity.ok(new MessageResponse("Usuário deletado com sucesso!"));
        
    }

}






