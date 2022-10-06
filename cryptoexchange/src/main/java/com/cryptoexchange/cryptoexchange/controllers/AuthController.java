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

    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(

            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal(); 
        
        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
    
        return ResponseEntity.ok(new UserInfoResponse(jwt,
                            refreshToken.getToken(),
                            userDetails.getId(), 
                            userDetails.getUsername(), 
                            userDetails.getEmail(), 
                            roles));
    }


    @ApiOperation(value = "Criando um novo usuário", consumes = "application/json", produces = "application/json")
    //localhost:8080/api/auth/signup - POST
    @PostMapping("/signup")

    //Documentação do Swagger
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 201, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })

    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: Username is already taken!"));
            }
        
            if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: Email is already in use!"));
            }
        
            // Create new user's account
            User user = new User(signUpRequest.getUsername(), 
                    signUpRequest.getEmail(),
                    encoder.encode(signUpRequest.getPassword()));
        
            Set<String> strRoles = signUpRequest.getRole();

            Set<Role> roles = new HashSet<>();
        
            if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
            } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);
            
                    break;
                    case "premium":
                    Role modRole = roleRepository.findByName(ERole.ROLE_PREMIUM)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(modRole);
            
                    break;
                    default:
                    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                    }
            });
            }
        
            user.setRoles(roles);
            
            userRepository.save(user);
        
            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
            .map(refreshTokenService::verifyExpiration)
            .map(RefreshToken::getUser)
            .map(user -> {
                String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
            })
            .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                "Refresh token is not in database!"));
        }    


    @ApiOperation(value = "Logout do usuário")
    //localhost:8080/api/auth/signout - POST
    
    @PostMapping("/signout")

    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("Sessão encerrada"));
    }

    /*@PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getId();
        refreshTokenService.deleteByUserId(userId);
        return ResponseEntity.ok(new MessageResponse("Log out successful!"));
    }*/

    @ApiOperation(value = "Buscando usuários por nome", produces = "application/json")
    //localhost:8080/api/auth/users/?username=bob - GET
    @GetMapping("/users")

    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    
    public ResponseEntity<Optional<User>> findByName(@RequestParam String username){

        Optional<User> _users = userRepository.findByUsername(username);

        if(_users.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

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

        Optional<User> _user = userRepository.findById(id);

        if (_user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);   
        }

        return new ResponseEntity<Optional<User>>(_user, HttpStatus.OK);

    }



}



