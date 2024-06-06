package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.Optional;

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
        return "users_list";
    }

    @GetMapping("/registration")
    public String register(Model model){
      User user = new User();
      model.addAttribute("user", user);
      return "register";
        }

        @GetMapping("/addUser")
        public String addUser(@ModelAttribute("user") User user){
         userRepository.save(user);
         return "redirect:/admin";
        }

    @PostMapping("/edit")
    public String updateUser(@RequestParam("id") long id, Model model) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            model.addAttribute("user", user);
            return "user";
        } else throw new UsernameNotFoundException(
                String.format("Username with Id = %s not found", id));
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam("id") long id) {
        userRepository.deleteById(id);
        return "redirect:/admin";
    }


}
