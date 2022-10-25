package com.cryptoexchange.cryptoexchange.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cryptoexchange.cryptoexchange.models.User;


// Repositório criado com JPARepository para implementação das imformações no BD
public interface UserRepository extends JpaRepository<User, Long> {
    
    List<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    User getUserById(Long id);

    User findById(long id);
    
    Optional<User> getByUsername(String username);

}
