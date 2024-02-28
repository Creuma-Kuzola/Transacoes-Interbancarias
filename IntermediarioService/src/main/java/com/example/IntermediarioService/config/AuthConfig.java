package com.example.IntermediarioService.config;

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
                .requestMatchers(HttpMethod.POST, "/api/intermediario/auth/*").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/*").permitAll()
                        .requestMatchers(HttpMethod.GET,"/session").permitAll()
                        .requestMatchers(HttpMethod.POST.GET,"/deep").permitAll()
                        .requestMatchers(HttpMethod.POST,"/transferencia").hasRole("CLIENTE")
                        .requestMatchers(HttpMethod.POST,"/transferencia/interbancaria").hasRole("CLIENTE")

                        .requestMatchers(HttpMethod.POST,"/transferencia/sendSolicitacaoTransferenciaKuzola").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST,"/transferencia/publishTransferencia").hasRole("CLIENTE")
                        .requestMatchers(HttpMethod.POST,"/transferencia/response").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/transferencia/responseTokuzola").hasRole("ADMIN")



                        .requestMatchers(HttpMethod.GET, "/transferencia/id/").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/transferencia/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/transferencia/*").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/transferencia/historico/credito").hasRole("CLIENTE")
                        .requestMatchers(HttpMethod.GET, "/transferencia/historico/debito").hasRole("CLIENTE")
                        .requestMatchers(HttpMethod.GET, "/transferencia/saldo").hasRole("CLIENTE")

                        .requestMatchers(HttpMethod.POST,"/banco").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/banco/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/banco").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/banco/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/banco/*").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST,"/cliente").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/cliente/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/cliente").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/cliente/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/cliente/*").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST,"/funcionario").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/funcionario/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/funcionario").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/funcionario/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/funcionario/*").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST,"/user").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/user/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/user").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/user/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/user/*").hasRole("ADMIN")

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
