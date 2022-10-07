package com.cryptoexchange.cryptoexchange.payloads.requests;

import java.util.Set;

import javax.validation.constraints.*;


// Dados que devem ser inseridos pelo usuário em um pedido de cadastro
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 25)
    private String username;

    @NotBlank
    @Size(max = 80)
    @Email
    private String email;
    
    private Set<String> role;
    
    @NotBlank
    @Size(min = 6, max = 40  )
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public Set<String> getRole() {
        return this.role;
    }
    
    public void setRole(Set<String> role) {
        this.role = role;
    }
}
