package com.example.login_auth_api.infra.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.login_auth_api.domain.user.User;
import com.example.login_auth_api.exception.JwtException;
import com.example.login_auth_api.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);

        if (token != null) {
            try {
                var jwt = tokenService.validateToken(token);
                    User user = userRepository.findByEmail(jwt).orElseThrow(() -> new JWTVerificationException("expired or invalid token"));
                    var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
                    var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JWTVerificationException ex) {
                response.getWriter().write(ex.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }



    private String recoverToken(HttpServletRequest request) {
        String autorizationHeader = request.getHeader("Authorization");
        if (autorizationHeader != null) {
            return autorizationHeader.replace("Bearer ", "");

        }
        return null;
    }
}