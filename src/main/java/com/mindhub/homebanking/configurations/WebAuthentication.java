package com.mindhub.homebanking.configurations;

import com.mindhub.homebanking.models.Cliente;
import com.mindhub.homebanking.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebAuthentication extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    ClienteRepository clienteRepository;



    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(email-> {

            Cliente cliente = clienteRepository.findByEmail(email);

            if (cliente != null) {
                if(cliente.getEmail().contains("ADMINMINDHUB@ADMIN.COM")){
                    return new User(cliente.getEmail(), cliente.getPassword(),

                            AuthorityUtils.createAuthorityList("ADMIN"));
                } else {

                    return new User(cliente.getEmail(), cliente.getPassword(),

                            AuthorityUtils.createAuthorityList("CLIENT"));
                }

            } else {

                throw new UsernameNotFoundException("Usuario Desconocido: " + email);

            }

        });

    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();

    }

}