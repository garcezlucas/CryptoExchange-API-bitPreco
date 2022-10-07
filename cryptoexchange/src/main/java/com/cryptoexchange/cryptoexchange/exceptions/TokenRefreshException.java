package com.cryptoexchange.cryptoexchange.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


// Responsavel por resgatar as exeções lançadas pela JVM
@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenRefreshException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TokenRefreshException(String token, String message) {
    super(String.format("Failed for [%s]: %s", token, message));
    }
}
