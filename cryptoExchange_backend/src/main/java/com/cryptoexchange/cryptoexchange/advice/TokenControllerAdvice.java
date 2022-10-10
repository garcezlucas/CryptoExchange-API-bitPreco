package com.cryptoexchange.cryptoexchange.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


// Trata as exceções do refreshToken retornando mensagem de falha
@RestControllerAdvice
public class TokenControllerAdvice {

    @ResponseStatus(HttpStatus.FORBIDDEN)
    public class TokenRefreshException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TokenRefreshException(String token, String message) {
            super(String.format("Failed for [%s]: %s", token, message));
        }
    }
}
