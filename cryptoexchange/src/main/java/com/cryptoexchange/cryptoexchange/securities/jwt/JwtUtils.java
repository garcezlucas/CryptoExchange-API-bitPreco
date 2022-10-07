package com.cryptoexchange.cryptoexchange.securities.jwt;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.cryptoexchange.cryptoexchange.securities.services.UserDetailsImpl;

import io.jsonwebtoken.*;


@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${cryptoexchange.app.jwtSecret}")
    private String jwtSecret;

    @Value("${cryptoexchange.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${cryptoexchange.app.jwtCookieName}")
    private String jwtCookie;

    // Busca os dados do usuário para geração do token
    public String generateJwtToken(UserDetailsImpl userPrincipal) {
        // Retorna o nome de usuário para geração do token
        return generateTokenFromUsername(userPrincipal.getUsername());
    }
    // Gera o token para o usuário através do nome de usuário
    public String generateTokenFromUsername(String username) {
        // Retorna o token gerado para o usuário
        return Jwts.builder().setSubject(username).setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
    }    
    // Pega o nome de usuário através do token
    public String getUserNameFromJwtToken(String token) {
        // Retorna  
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    // Obtem o token Jwt do usuário
    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie userPrincipal = WebUtils.getCookie(request, jwtCookie);
        // Verifica se o usuário é diferente de nulo
        if (userPrincipal != null) {
            // REtorna o valor do token para o usuário principal
            return userPrincipal.getValue();
        } else {
            // Retorna nulo em caso do usuário ser nulo
            return null;
        }
    }
    // Realiza a validação boolean do token
    public boolean validateJwtToken(String authToken) {
        // Verifica se as reivindicações esão corretas
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            // Retorna que os reivindicações são validas
            return true;
        // Retorna uma mensagem de erro de assinatura
        } catch (SignatureException e) {
            logger.error("Assinatura JWT Inválida: {}", e.getMessage());
        // Retorna uma mensagem de erro de token invalido
        } catch (MalformedJwtException e) {
            logger.error("Token JWT Inválido: {}", e.getMessage());
        // Retorna uma mensagem de erro de token expirado
        } catch (ExpiredJwtException e) {
            logger.error("Token JWT está expirado: {}", e.getMessage());
        // Retorna uma mensagem de erro de token não suportado
        } catch (UnsupportedJwtException e) {
            logger.error("Token JWT não é suportado : {}", e.getMessage());
        // Retorna uma mensagem de erro de reivindicações vazias
        } catch (IllegalArgumentException e) {
            logger.error("As reivindicações estão vazias: {}", e.getMessage());
        }
        // Retorna falso se as reivindicações estão erradas 
        return false;
    }
    // Realiza a limpeza do cookies com o token JWT 
    public ResponseCookie getCleanJwtCookie() {
        // Seta um valor nulo para o cookie
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/api").build();
    //Retorna o cookie com valor nulo
    return cookie;
    }

    
    }
