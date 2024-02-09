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
                .requestMatchers(HttpMethod.POST,"/pessoa").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/pessoa").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/pessoa/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST,"/cliente").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/cliente").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/cliente/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/ContaBancaria").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/ContaBancaria").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/ContaBancaria/*").hasRole("ADMIN")
            .requestMatchers(HttpMethod.POST, "/api/v1/books").hasRole("ADMIN")
            .requestMatchers(HttpMethod.POST, "/transferencia").hasRole("CLIENTE")
                .requestMatchers(HttpMethod.GET,"/ContaBancaria/saldo/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/ContaBancaria/saldo/*").hasRole("CLIENTE")
                .requestMatchers(HttpMethod.PUT,"/ContaBancaria/deposite/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH,"/ContaBancaria/transfer/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/empresa").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/empresa").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/empresa/*").hasRole("ADMIN")
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