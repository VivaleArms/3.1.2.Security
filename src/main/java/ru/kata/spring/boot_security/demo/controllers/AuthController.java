package ru.kata.spring.boot_security.demo.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.services.RoleServiceImpl;

@Controller
public class AuthController {

    private final DaoAuthenticationProvider daoAuthenticationProvider;

    private final UserRepository userRepository;
    private final RoleServiceImpl roleService;

    @Autowired
    public AuthController(DaoAuthenticationProvider daoAuthenticationProvider, UserRepository userRepository, RoleServiceImpl roleService) {
        this.daoAuthenticationProvider = daoAuthenticationProvider;
        this.userRepository = userRepository;
        this.roleService = roleService;
    }


    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("role",
                roleService.getAllRoles()
        );
        return "register";
    }

    @PostMapping("/register")
    public String createUser(HttpServletRequest httpServletRequest,
                             @ModelAttribute("user") User user) {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    user.getName(),
                    user.getPassword()
            );
            user = userRepository.save(user);

            Authentication authentication = daoAuthenticationProvider.authenticate(token);
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);
            HttpSession session = httpServletRequest.getSession(true);
            session.setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    securityContext
            );
            return "redirect:/user";
        } catch (Exception e) {
            return "redirect:/register?error";
        }
    }
}
