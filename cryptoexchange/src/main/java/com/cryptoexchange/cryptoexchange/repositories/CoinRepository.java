package com.cryptoexchange.cryptoexchange.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cryptoexchange.cryptoexchange.models.Coin;


// Repositório criado com JPARepository para implementação das imformações no BD
public interface CoinRepository  extends JpaRepository<Coin, Long>{

    List<Coin> findById(Integer id);

    Coin getCoinById(Integer id);

    @Transactional
    void deleteById(Integer id);

}