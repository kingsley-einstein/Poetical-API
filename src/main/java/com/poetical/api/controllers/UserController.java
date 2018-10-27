package com.poetical.api.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.crypto.bcrypt.BCrypt;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.poetical.api.repositories.UserRepository;
import com.poetical.api.repositories.RoleRepository;
import com.poetical.api.models.User;
import com.poetical.api.models.Role;
import com.poetical.api.auth.AuthUserDetailsService;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private AuthUserDetailsService authUserDetailsService;

    @GetMapping(value = "/{page}")
    @ResponseBody
    public Page<User> getUsers(@PathVariable(name = "page") Integer page) {
        Pageable pageable = PageRequest.of(page, 25);
        return repo.findAll(pageable);
    }

    @PostMapping(value = "/register")
    @ResponseBody
    public User createUser(@RequestBody Map<String, String> body) {
        User user = new User(body.get("email"), BCrypt.gensalt(16), body.get("username"), new Date(), true);
        user.setPassword(BCrypt.hashpw(body.get("password"), user.getSalt()));
        User newUser = repo.save(user);
        roleRepo.save(new Role("user", newUser));

        return newUser;
    }

    @PostMapping(value = "/login")
    @ResponseBody
    public User logUserIn(@RequestBody Map<String, String> body) {
        User user = repo.findByUsername(body.get("username"));
        authUserDetailsService.loadUserByUsername(user.getUsername());
        return user;
    }

}