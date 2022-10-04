package com.cryptoexchange.cryptoexchange.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cryptoexchange.cryptoexchange.model.Coin;

public interface CoinRepository  extends JpaRepository<Coin, Long>{

    List<Coin> findById(Integer id);

    @Transactional
    void deleteById(Integer id);

    @Transactional
    void getById(Integer id);

    Coin getCoinById(Integer id);



}