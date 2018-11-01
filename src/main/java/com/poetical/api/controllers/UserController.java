package com.poetical.api.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.poetical.api.repositories.UserRepository;
import com.poetical.api.repositories.RoleRepository;
import com.poetical.api.models.User;
import com.poetical.api.models.Role;
import com.poetical.api.auth.AuthUserDetailsService;
import com.poetical.api.exceptions.IncorrectPasswordException;
import com.poetical.api.exceptions.NotFoundException;

import java.util.Date;
import java.util.Map;
//import java.util.Optional;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private AuthUserDetailsService authUserDetailsService;

    @GetMapping(value = "/all/{page}")
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

        if (user != null) {
            if (user.checkPassword(BCrypt.hashpw(body.get("password"), user.getSalt()))) {
                authUserDetailsService.loadUserByUsername(user.getUsername());
                return user;
            }
            else {
                throw new IncorrectPasswordException("Incorrect Password");
            }
        }
        else {
            throw new NotFoundException(String.format("User with username %s not found", body.get("username")));
        }
    }

    @PutMapping(value = "/change_email")
    @ResponseBody
    public String changeEmail(@RequestParam("email") String email, @RequestBody Map<String, String> body) {
        User user = repo.findByEmail(email);
        
        if (user != null) {
            
            user.setEmail(body.get("email"));
            repo.save(user);
            
            return String.format("Email updated to %s", body.get("email"));
        }
        else {
            throw new NotFoundException(String.format("User with email %s not found", body.get("email")));
        }

        
    }

    @PutMapping(value = "/change_password")
    @ResponseBody
    public String changePassword(@RequestParam("id") Long id, @RequestBody Map<String, String> body) {
        User user = repo
                    .findById(id)
                    .orElseThrow(() -> new NotFoundException("User with given id not found"));
                
        user.setSalt(BCrypt.gensalt(16));
        user.setPassword(BCrypt.hashpw(body.get("password"), user.getSalt()));
        repo.save(user);

        return String.format("Password updated for user %s", user.getUsername());
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        repo.deleteById(id);
    }

}