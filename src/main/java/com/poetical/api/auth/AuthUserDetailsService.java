package com.poetical.api.auth;

import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.poetical.api.models.User;

import org.springframework.beans.factory.annotation.Autowired;
//simport org.springframework.beans.factory.annotation.Value;

@Service(value = "userService")
public class AuthUserDetailsService implements UserDetailsService {

    @Autowired
    AuthDAO authDAO;

    @Override
    public Auth2User loadUserByUsername(String username) {
        try {
            User user = authDAO.loadUserByUsername(username);
            Auth2User auth2User = new Auth2User(user);
            System.out.println("User logged in with authorities --- "+auth2User.getAuthorities());

            return auth2User;
        }
        catch (Exception e) {
            throw new UsernameNotFoundException("You are unauthorized to access this resource");
        }
    }
}