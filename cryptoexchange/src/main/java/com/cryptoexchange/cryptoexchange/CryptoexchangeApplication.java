package com.cryptoexchange.cryptoexchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
@EnableFeignClients
@ComponentScan({"com.cryptoexchange.cryptoexchange"})
@EnableWebMvc
public class CryptoexchangeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CryptoexchangeApplication.class, args);
	}

}
