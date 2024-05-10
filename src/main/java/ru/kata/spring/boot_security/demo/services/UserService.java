package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.Set;

public interface UserService {
    Set<User> getAllUsers();
    User getUserById(long id);
    Set<Role> getRolesById(long id);
    User createUser(User user);
    User updateUser(User user);
    boolean deleteUserById(long id);





}
