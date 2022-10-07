package com.cryptoexchange.cryptoexchange.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cryptoexchange.cryptoexchange.models.User;


// Repositório criado com JPARepository para implementação das imformações no BD
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    User getUserById(Long id);
}
