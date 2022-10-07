package com.cryptoexchange.cryptoexchange.payloads.requests;


import javax.validation.constraints.NotBlank;


// Dados necessários para a requisição de um refreshToken
public class TokenRefreshRequest {
    @NotBlank
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
