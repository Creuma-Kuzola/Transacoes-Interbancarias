package com.example.KuzolaBankService.config.auth;

import com.example.KuzolaBankService.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
//import ucan.edu.entities.Conta;
//import ucan.edu.repository.ContaRepository;
//import ucan.edu.repository.UserRepository;

@Component
public class SecurityFilter extends OncePerRequestFilter
{

    @Autowired
    TokenProvider tokenService;
    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        var token = this.recoverToken(request);
        if (token != null)
        {
            var login = tokenService.validateToken(token);
            var user = userRepository.findByLogin(login);

            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request)
    {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null)
        {
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }


}
