package com.cryptoexchange.cryptoexchange.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cryptoexchange.cryptoexchange.exceptions.TokenRefreshException;
import com.cryptoexchange.cryptoexchange.models.ERole;
import com.cryptoexchange.cryptoexchange.models.RefreshToken;
import com.cryptoexchange.cryptoexchange.models.Role;
import com.cryptoexchange.cryptoexchange.models.User;
import com.cryptoexchange.cryptoexchange.payloads.requests.LoginRequest;
import com.cryptoexchange.cryptoexchange.payloads.requests.SignupRequest;
import com.cryptoexchange.cryptoexchange.payloads.responses.MessageResponse;
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
@CrossOrigin(origins = "*")
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
        @ApiResponse(code = 201, message = "OK"),
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
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        // Cria uma lista com a(s) Role atribuidas ao usuário
        List<String> roles = userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());
        // Chama a função createRefreshToken para gerar um novo refreshToken
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        ResponseCookie jwtRefreshCookie = jwtUtils.generateRefreshJwtCookie(refreshToken.getToken());
        //Retorna uma resposta com os dados do usuário
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                        .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                        .body(new UserInfoResponse(jwtCookie, refreshToken.getToken(),
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
        @ApiResponse(code = 201, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> refreshtoken(HttpServletRequest request) {
        // Chama a função getRefreshToken para pegar o RefreshToken
        String refreshToken = jwtUtils.getJwtRefreshFromCookies(request);
        // Faz uma verificação do refreshToken
        if ((refreshToken != null) && (refreshToken.length() > 0)) {
            // Retorna uma resposta com as informações do Refresh Token
            return refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    // Chama a função generateJwtToken para gerar um novo token 
                    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(user);
                     // Retorna as informações do Token gerado
                    return ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                        .header(HttpHeaders.SET_COOKIE, refreshToken)
                        .body(new MessageResponse("Token atualizado com sucesso!"));
                })
                // Retorna uma mensagem de Erro se o token não for encontrado no BD
                .orElseThrow(() -> new TokenRefreshException(refreshToken,
                    "Refresh token não está no Banco de Dados!"));
        }
        // Retorna uma mensagem de Erro se o refreshToken estiver vazio
        return ResponseEntity.badRequest().body(new MessageResponse("Refresh Token está vazio!"));
    }


    @ApiOperation(value = "Logout do usuário")
    //localhost:8080/api/auth/signout - POST
    @PostMapping("/signout")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 201, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> logoutUser() {
        // Pega o contexto de segurança
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Verifica se o usuário não é anônimo
        if (principle.toString() != "Usuário anônimo") {    
            // Pega o Id para exclusão  
            Long userId = ((UserDetailsImpl) principle).getId();
            // Exclui o usuário através do Id
            refreshTokenService.deleteByUserId(userId);
        }
        // Chama a funçaõ getCleanJwtCookie para limpar o cookie
        ResponseCookie jwtCookie = jwtUtils.getCleanJwtCookie();
        // Chama a funçaõ getCleanJwtRefreshCookie para limpar o refreshcookie
        ResponseCookie jwtRefreshCookie = jwtUtils.getCleanJwtRefreshCookie();
        // Retorna o novo cabeçalho Http sem as info do usuário
        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
            .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
            .body(new MessageResponse("Logout realizado com sucessso!"));
    }

}