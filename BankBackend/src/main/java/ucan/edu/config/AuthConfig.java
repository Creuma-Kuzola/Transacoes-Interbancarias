package ucan.edu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ucan.edu.config.auth.SecurityFilter;


@Configuration
//@EnableWebSecurity
public class AuthConfig {
  @Autowired
  SecurityFilter securityFilter;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authorize -> authorize

            .requestMatchers(HttpMethod.POST, "/api/v1/auth/*").permitAll()

                .requestMatchers(HttpMethod.GET, "/api/v1/auth/logout").hasAnyRole("ADMIN","CLIENTE")

                .requestMatchers(HttpMethod.POST,"/pessoa").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/pessoa/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/pessoa").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,"/pessoa/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/pessoa/*").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST,"/cliente").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/cliente").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/cliente/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,"/cliente/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/cliente/*").hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET,"/ContaBancaria/saldo").hasRole("CLIENTE")
                .requestMatchers(HttpMethod.GET,"/ContaBancaria/infoBancarias").hasRole("CLIENTE")

                .requestMatchers(HttpMethod.GET,"/ContaBancaria").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/ContaBancaria/id/").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/ContaBancaria").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/ContaBancaria/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/ContaBancaria/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/ContaBancaria/transfer/*").hasRole("ADMIN")

                .requestMatchers(HttpMethod.PUT, "/ContaBancaria/deposite/*").hasAnyRole("ADMIN", "CLIENTE")
                //.requestMatchers(HttpMethod.GET, "/ContaBancaria/saldo/*").hasAnyRole("ADMIN","CLIENTE")

                .requestMatchers(HttpMethod.GET,"/ContaBancaria/saldo/*").hasAnyRole("ADMIN","CLIENTE")

            .requestMatchers(HttpMethod.POST, "/api/v1/books").hasRole("ADMIN")

            .requestMatchers(HttpMethod.POST, "/transferencia").hasRole("CLIENTE")
                .requestMatchers(HttpMethod.GET, "/transferencia/historico/debito").hasRole("CLIENTE")
                .requestMatchers(HttpMethod.GET, "/transferencia/historico/credito").hasRole("CLIENTE")
                .requestMatchers(HttpMethod.POST, "/transferencia").hasRole("CLIENTE")
                .requestMatchers(HttpMethod.POST, "/transferencia/publishTransferencia").hasRole("CLIENTE")

                .requestMatchers(HttpMethod.GET, "/transferencia/id/").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/transferencia/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/transferencia/*").hasRole("ADMIN")
               // .requestMatchers(HttpMethod.GET,"/ContaBancaria/saldo/*").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/empresa").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/empresa/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/empresa").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/empresa/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/empresa/*").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/funcionario").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/funcionario/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/funcionario").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/funcionario/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/funcionario/*").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/localizacao").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/localizacao/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/localizacao").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/localizacao/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/localizacao/*").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/ola/").hasRole("CLIENTE")
            .anyRequest().authenticated())
            .exceptionHandling(Customizer.withDefaults())
        .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}