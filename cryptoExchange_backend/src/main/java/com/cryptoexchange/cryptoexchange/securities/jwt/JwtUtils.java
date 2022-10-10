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

import com.cryptoexchange.cryptoexchange.models.User;
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
    
    @Value("${cryptoexchange.app.jwtRefreshCookieName}")
    private String jwtRefreshCookie;

    // // Busca os dados do usuário Principal para geração do token
    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
        String jwt = generateTokenFromUsername(userPrincipal.getUsername());   
        return generateCookie(jwtCookie, jwt, "/api");
    }
    // Busca os dados do usuário para geração do token
    public ResponseCookie generateJwtCookie(User user) {
        String jwt = generateTokenFromUsername(user.getUsername());   
        return generateCookie(jwtCookie, jwt, "/api");
    }
    // Gera o refresheToken
    public ResponseCookie generateRefreshJwtCookie(String refreshToken) {
        return generateCookie(jwtRefreshCookie, refreshToken, "/api/auth/refreshtoken");
    }
    // Gera o token para o usuário através do nome de usuário
    public String generateTokenFromUsername(String username) {   
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
    }
    // Gera o cookie
    private ResponseCookie generateCookie(String name, String value, String path) {
        ResponseCookie cookie = ResponseCookie.from(name, value).path(path).maxAge(24 * 60 * 60).httpOnly(true).build();
        return cookie;
    }
    // Pega o valor do cookie do usuário
    private String getCookieValueByName(HttpServletRequest request, String name) {
        Cookie cookie = WebUtils.getCookie(request, name);
        if (cookie != null) {
        return cookie.getValue();
        } else {
        return null;
        }
    }
    // Pega o valor do cookie do token
    public String getJwtFromCookies(HttpServletRequest request) {
        return getCookieValueByName(request, jwtCookie);
    }
    // Pega o valor do cookie do refreshToken
    public String getJwtRefreshFromCookies(HttpServletRequest request) {
        return getCookieValueByName(request, jwtRefreshCookie);
    }
    // Limpa o cookie do token
    public ResponseCookie getCleanJwtCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/api").build();
        return cookie;
    }
    // Limpa o cookie do refresheToken
    public ResponseCookie getCleanJwtRefreshCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtRefreshCookie, null).path("/api/auth/refreshtoken").build();
        return cookie;
    }
    // Pega o nome do cookie do usuário
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }
     // Realiza a validação booleana do token
    public boolean validateJwtToken(String authToken) {
        try {
        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
        return true;
        } catch (SignatureException e) {
        logger.error("Assinatura JWT Inválida: {}", e.getMessage());
        } catch (MalformedJwtException e) {
        logger.error("Token JWT Inválido: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
        logger.error("Token JWT está expirado: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
        logger.error("Token JWT não é suportado: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
        logger.error("As reivindicações estão vazias: {}", e.getMessage());
        }

        return false;
    }
    
}
