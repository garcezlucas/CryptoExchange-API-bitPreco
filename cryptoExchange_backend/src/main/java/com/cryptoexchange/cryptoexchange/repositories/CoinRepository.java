package com.cryptoexchange.cryptoexchange.repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cryptoexchange.cryptoexchange.models.Coin;


// Repositório criado com JPARepository para implementação das imformações no BD
public interface CoinRepository  extends JpaRepository<Coin, Long>{

    Optional<Coin> findById(Long id);

    Coin getCoinById(Long id);

    @Transactional
    void deleteById(Long id);

    Coin getCoinByMarket(String market);

    List<Coin> findCoinByMarket(String market);

}