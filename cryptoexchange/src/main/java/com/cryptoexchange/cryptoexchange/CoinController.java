
package com.cryptoexchange.cryptoexchange;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api")
public class CoinController {

    @Autowired
    private CoinInterface coinInterface;

    @GetMapping("/{market}/ticker")
    public ResponseEntity<Coin> getByCep(@PathVariable String market) {

        Coin coin = coinInterface.getCoinByCoin(market);

        return coin != null ? ResponseEntity.ok().body(coin)
                            : ResponseEntity.notFound().build();

    }
}