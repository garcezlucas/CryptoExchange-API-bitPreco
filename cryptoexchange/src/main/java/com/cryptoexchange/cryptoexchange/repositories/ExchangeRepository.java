package com.cryptoexchange.cryptoexchange.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cryptoexchange.cryptoexchange.models.Exchange;
import com.cryptoexchange.cryptoexchange.payloads.responses.ExchangeResponse;


// Repositório criado com JPARepository para implementação das imformações no BD
public interface ExchangeRepository extends JpaRepository<Exchange, Long>{

    List<Exchange> findByExchange(String exchange);

    Exchange getExchangeById(Integer id);

    @Transactional
    void deleteById(Integer id);

    @Transactional
    void save(ExchangeResponse exchange);

}
