package com.poetical.api.auth;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.poetical.api.repositories.UserRepository;
import com.poetical.api.models.User;
import com.poetical.api.models.Role;

import java.util.List;
import java.util.ArrayList;

@Repository
public class AuthDAO {

    @Autowired
    private UserRepository repo;

    public User loadUserByUsername(String username) {

        User user = repo.findByUsername(username);
        List<Role> roles = user.getRoles();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        roles
        .stream()
        .forEach((role) -> {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        });

        user.setGrantedAuthoritiesList(grantedAuthorities);

        return repo.save(user);
    }
}