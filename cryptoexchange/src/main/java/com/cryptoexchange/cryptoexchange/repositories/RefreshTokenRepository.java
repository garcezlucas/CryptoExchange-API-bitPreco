package com.cryptoexchange.cryptoexchange.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.cryptoexchange.cryptoexchange.models.RefreshToken;
import com.cryptoexchange.cryptoexchange.models.User;

@Repository

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Modifying
    int deleteByUser(User user);
}
