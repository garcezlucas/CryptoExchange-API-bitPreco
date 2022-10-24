package com.cryptoexchange.cryptoexchange.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cryptoexchange.cryptoexchange.models.Exchange;
import com.cryptoexchange.cryptoexchange.payloads.responses.ExchangeResponse;




// Repositório criado com JPARepository para implementação das imformações no BD
public interface ExchangeRepository extends JpaRepository<Exchange, Long>{

    @Query(value = "SELECT * FROM exchange WHERE u_id = :u_id OR u_id IS NULL" , nativeQuery = true)
    List<Exchange> findByUser(@Param("u_id") long id);

    List<Exchange> findByExchange(String exchange);

    Exchange getExchangeById(Long id);

    @Transactional
    void deleteById(Long id);

    @Transactional
    void save(ExchangeResponse exchange);

}
