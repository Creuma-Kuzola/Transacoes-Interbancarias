package com.example.KuzolaBankService.config;

import com.example.KuzolaBankService.config.auth.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import ucan.edu.config.auth.SecurityFilter;

@Configuration
@EnableWebSecurity
public class AuthConfig
{

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
    {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.POST, "/api/v1/auth/*").permitAll()
                        .requestMatchers(HttpMethod.GET,"/session").permitAll()
                .requestMatchers(HttpMethod.GET, "/ContaBancaria").permitAll()
                .requestMatchers(HttpMethod.POST, "/transferencia").hasRole("CLIENTE")
                .requestMatchers(HttpMethod.GET, "/transferencia").hasRole("CLIENTE")
                .requestMatchers(HttpMethod.POST, "/pessoa").hasRole("CLIENTE")
                .requestMatchers(HttpMethod.GET, "/pessoa").hasRole("CLIENTE")
                .requestMatchers(HttpMethod.POST, "/cliente").hasRole("CLIENTE")
                .requestMatchers(HttpMethod.GET, "/cliente").hasRole("CLIENTE")
                .requestMatchers(HttpMethod.POST, "/empresa").hasRole("CLIENTE")
                .requestMatchers(HttpMethod.GET, "/empresa").hasRole("CLIENTE")
                .requestMatchers(HttpMethod.POST, "/funcionario").hasRole("CLIENTE")
                .requestMatchers(HttpMethod.GET, "/funcionario").hasRole("CLIENTE")
                .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception
    {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
