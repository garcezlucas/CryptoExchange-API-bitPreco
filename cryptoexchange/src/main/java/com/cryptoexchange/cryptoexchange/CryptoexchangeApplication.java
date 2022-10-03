package com.cryptoexchange.cryptoexchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class CryptoexchangeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CryptoexchangeApplication.class, args);
	}

}
