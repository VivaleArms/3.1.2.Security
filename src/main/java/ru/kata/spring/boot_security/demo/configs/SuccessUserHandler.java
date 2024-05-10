package ru.kata.spring.boot_security.demo.configs;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {

    // Spring Security использует объект Authentication, пользователя авторизованной сессии.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Set<? extends GrantedAuthority> roles = new HashSet<>(authentication.getAuthorities());
        if(roles.stream().allMatch(r -> r.getAuthority().contains("ADMIN"))) {
            response.sendRedirect("/admin");
        } else if (roles.stream().anyMatch(r -> r.getAuthority().contains("USER"))) {
            response.sendRedirect("/user");
        } else {
            response.sendRedirect("/");
        }
    }
}