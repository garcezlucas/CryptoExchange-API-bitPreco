package com.cryptoexchange.cryptoexchange.securities.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cryptoexchange.cryptoexchange.models.User;
import com.cryptoexchange.cryptoexchange.repositories.UserRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    UserRepository userRepository;
    // Busca o usuário no BD
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca o usuario através de seu nome de usuário
        User user = userRepository.findByUsername(username)
                // Retorna um erro de usuário não encontrado
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o nome de usuário: " + username));
        // Retorna o usuário se encontrado
        return UserDetailsImpl.build(user);
    }
}
