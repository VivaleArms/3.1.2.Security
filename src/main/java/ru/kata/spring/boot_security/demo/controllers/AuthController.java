package ru.kata.spring.boot_security.demo.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

@Controller
public class AuthController {
    private final UserServiceImpl userService;
    private final DaoAuthenticationProvider daoAuthenticationProvider;

    @Autowired
    public AuthController(UserServiceImpl userService, DaoAuthenticationProvider daoAuthenticationProvider) {
        this.userService = userService;
        this.daoAuthenticationProvider = daoAuthenticationProvider;
    }

    @GetMapping("/register")
    public String createUser(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse,
                             @ModelAttribute("user")User user) {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    user.getEmail(),
                    user.getPassword()
            );
            user = userService.createUser(user);

            if (user == null) {
                return "redirect:/register?error";
            }
            Authentication authentication = daoAuthenticationProvider.authenticate(token);
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);
            HttpSession session = httpServletRequest.getSession(true);
            session.setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    securityContext
            );
            return "redirect:/user";
        } catch (Exception e){
            return "redirect:/register?error";
        }
    }
}
