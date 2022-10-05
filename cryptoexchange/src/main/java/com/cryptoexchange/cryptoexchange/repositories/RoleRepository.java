package com.cryptoexchange.cryptoexchange.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cryptoexchange.cryptoexchange.models.ERole;
import com.cryptoexchange.cryptoexchange.models.Role;


// Repositório criado com JPARepository para implementação das imformações no BD
@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
    
    Optional<Role> findByName(ERole name);
}
