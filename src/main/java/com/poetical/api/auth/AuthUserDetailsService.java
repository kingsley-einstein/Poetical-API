package com.poetical.api.auth;

import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.poetical.api.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Service(value = "UserService")
public class AuthUserDetailsService implements UserDetailsService {

    @Autowired
    AuthDAO authDAO;

    @Value("${poetical.username}")
    private String poeticalid;

    @Value("${poetical.password}")
    private String poeticalpassword;

    public Auth2User loadUserByUsername(String username) {
        try {
            User user = authDAO.loadUserByUsername(username);
            Auth2User auth2User = new Auth2User(user);
            System.out.println(poeticalid+" "+poeticalpassword);
            System.out.println(auth2User.getPassword());

            return auth2User;
        }
        catch (Exception e) {
            throw new UsernameNotFoundException("You are unauthorized to access this resource");
        }
    }
}