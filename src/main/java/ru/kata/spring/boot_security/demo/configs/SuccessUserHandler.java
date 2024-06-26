package ru.kata.spring.boot_security.demo.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {

    // Spring Security использует объект Authentication, пользователя авторизованной сессии.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        Set<? extends GrantedAuthority> roles = new HashSet<>(authentication.getAuthorities());
        if(roles.stream().allMatch(r -> r.getAuthority().contains("ROLE_ADMIN"))) {
            response.sendRedirect("/admin");
        } else if (roles.stream().anyMatch(r -> r.getAuthority().contains("ROLE_USER"))) {
            response.sendRedirect("/user");
        } else {
            response.sendRedirect("/");
        }
    }
}