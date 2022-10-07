package com.cryptoexchange.cryptoexchange.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.cryptoexchange.cryptoexchange.models.RefreshToken;
import com.cryptoexchange.cryptoexchange.models.User;


// Repositório criado com JPARepository para implementação das imformações no BD
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Modifying
    int deleteByUser(User user);
}
