package com.cryptoexchange.cryptoexchange.securities.services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cryptoexchange.cryptoexchange.exceptions.TokenRefreshException;
import com.cryptoexchange.cryptoexchange.models.RefreshToken;
import com.cryptoexchange.cryptoexchange.repositories.RefreshTokenRepository;
import com.cryptoexchange.cryptoexchange.repositories.UserRepository;

@Service
public class RefreshTokenService {
    @Value("${cryptoexchange.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    // Busca o token
    public Optional<RefreshToken> findByToken(String token) {
        // Retorna o token buscado
        return refreshTokenRepository.findByToken(token);
    }
    // Cria o refreshToken através do Id do usuário
    public RefreshToken createRefreshToken(Long userId) {
        // cria um novo refreshToken
        RefreshToken refreshToken = new RefreshToken();
        // Adicona o usuário, data de expiração e token ao refreshToken
        refreshToken.setUser(userRepository.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());
        // Salva o refreshToken no BD
        refreshToken = refreshTokenRepository.save(refreshToken);
        //Retorna o refreshToken
        return refreshToken;
    }
    // Verifica a expiração do refreshToken
    public RefreshToken verifyExpiration(RefreshToken token) {
        //Verifca se o token está expirado
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
        // Deleta o token do refreshToken 
        refreshTokenRepository.delete(token);
        // Lança uma mensagem de erro pedindo um novo sigin pois o refreshToken está expirado
        throw new TokenRefreshException(token.getToken(), "Refresh token está expirado. Por favor, faça um novo Login");
        }
        // Retorna o token
        return token;
    }
    // Deleta o refreshToken do BD através do Id de usuário
    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }
}