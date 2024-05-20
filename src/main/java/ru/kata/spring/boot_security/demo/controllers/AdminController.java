package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserRepository userRepository;

    @Autowired
    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping
    public String showAllUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/all_users";
    }
}
